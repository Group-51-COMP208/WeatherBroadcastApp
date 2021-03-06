package com.example.weatherbroadcastapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.lib.ApiException;
import com.example.lib.DetailedWeatherForecastSample;
import com.example.lib.Location;
import com.example.lib.WeatherForecastService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * View containing UK map which fetches and displays weather data from the forecast service
 * Designed to work only with the specific mercator projection map that it uses
 */
public class MapView extends View {
    private TextPaint mTextPaint;

    // The map appears to include the island of Hirta and possibly its neighbouring
    // island, Soay as the westmost location
    private static final double MAP_WESTMOST_LONGITUDE = -8.638;
    private static final double MAP_EASTMOST_LONGITUDE = 1.76;
    private static final double MAP_NORTHMOST_LATITUDE = 60.86;
    private static final double MAP_SOUTHMOST_LATITUDE = 49.86;

    private static final int ICON_DRAW_SIZE = 100;

    private final String[] significantLocationNames = {"London", "Brecon", "Manchester", "Glasgow", "Belfast", "Inverness"};
    private ArrayList<Location> significantLocations;
    private int currentSample = 0;
    private int numSamples = 0;

    Map<String, ArrayList<DetailedWeatherForecastSample>> samples = new HashMap<String, ArrayList<DetailedWeatherForecastSample>>();

    private Drawable mMapDrawable;

    public MapView(Context context) {
        super(context);
        init(null, 0);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MapView, defStyle, 0);

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.WHITE);

        mMapDrawable = ContextCompat.getDrawable(getContext(), R.drawable.map_mercator_uk);

        WeatherForecastService wfs = Services.get().getWeatherForecastService();
        significantLocations = new ArrayList<Location>();

        try {
            for (String lName : significantLocationNames) {
                Location l = wfs.getLocationByName(lName);
                if (l != null) {
                    significantLocations.add(l);
                } else {
                    System.err.println("ERROR in MapView. Significant location " + lName + " not recognized by service");
                }
            }

            for (Location l : significantLocations) {
                samples.put(l.getDisplayName(), wfs.getDetailedForecast(l));
            }

            numSamples = samples.get(significantLocations.get(0).getDisplayName()).size();
            for (Location l : significantLocations) {
                final int n = samples.get(l.getDisplayName()).size();
                if (n < numSamples) {
                    System.err.println("Location " + l.getDisplayName() +
                            " has fewer samples than other location(s), at" + String.valueOf(n)
                            + ". That probably shouldn't have happened...");
                }
            }
        }
        catch(ApiException e) {
            ExitDialog exitDialog = new ExitDialog();
            AppCompatActivity activity = (AppCompatActivity) getContext();
            exitDialog.show(activity.getSupportFragmentManager(), "Exit dialog");
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.sea_blue));

        mMapDrawable.setBounds(0,0, getWidth(), getHeight());
        mMapDrawable.draw(canvas);

        for(Location l: significantLocations) {
            final DetailedWeatherForecastSample s = samples.get(l.getDisplayName()).get(currentSample);
            Point drawCoords = toCanvasCoords(s.location.getLongitude(), s.location.getLatitude());

            Drawable iconDrawable = ContextCompat.getDrawable(getContext(), WeatherIcons.getIconId(s.weatherType));
            iconDrawable.setBounds(drawCoords.x, drawCoords.y, drawCoords.x + ICON_DRAW_SIZE,
                            drawCoords.y + ICON_DRAW_SIZE);
            iconDrawable.draw(canvas);

            // draw text of temp
            canvas.drawText(String.format(getContext().getString(R.string.n_degrees_c), (int)s.temperature_celsius),
                drawCoords.x,
                drawCoords.y,
                mTextPaint);
        }
    }

    public Point toCanvasCoords(double longitude, double latitude) {
        final double longitude_range = MAP_EASTMOST_LONGITUDE - MAP_WESTMOST_LONGITUDE;
        final double latitude_range  = MAP_NORTHMOST_LATITUDE - MAP_SOUTHMOST_LATITUDE;
        final double x_normalized    = (longitude - MAP_WESTMOST_LONGITUDE) / longitude_range;
        final double y_normalized    = (latitude - MAP_SOUTHMOST_LATITUDE) / latitude_range;
        return new Point((int) (x_normalized * getWidth()), (int) (getHeight() - y_normalized * getHeight()));
    }

    // The time for which forecasts are currently being displayed
    public Calendar getCurrentTime() {
        return samples.get(significantLocations.get(0).getDisplayName()).get(currentSample).timeStamp;
    }

    // Specifies the time to show the weather for as a zero to one ratio between the current
    // (real) time and the time of the latest cached sample. Intended for slider bars.
    public void setTimeNormalized(float time0to1) {
        if(time0to1 > 1.f)       time0to1 = 1.f;
        else if(time0to1 < 0.f)  time0to1 = 0.f;
        int newSample = (int) (time0to1 * (numSamples - 1));
        if(currentSample != newSample) {
            currentSample = newSample;
            invalidate();
        }
    }
}
