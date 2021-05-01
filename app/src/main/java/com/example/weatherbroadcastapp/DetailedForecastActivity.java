package com.example.weatherbroadcastapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lib.DetailedWeatherForecastSample;
import com.example.lib.Location;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailedForecastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);

        TableRow timeRow      = findViewById(R.id.row_time);
        TableRow typeRow      = findViewById(R.id.row_type);
        TableRow precipRow    = findViewById(R.id.row_precipitation);
        TableRow tempRow      = findViewById(R.id.row_temperature);
        TableRow uvRow        = findViewById(R.id.row_uvIndex);
        TableRow windDirRow   = findViewById(R.id.row_windDirection);
        TableRow windSpeedRow = findViewById(R.id.row_windSpeed);

        TextView label = new TextView(this);
        label.setText(getString(R.string.time_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        timeRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.type_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        typeRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.precipitation_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        precipRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.temperature_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        tempRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.uv_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        uvRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.wind_direction_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        windDirRow.addView(label);

        label = new TextView(this);
        label.setText(getString(R.string.wind_speed_row));
        label.setTextAppearance(R.style.table_label);
        label.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        windSpeedRow.addView(label);

        Location location = Services.get().getLocationService().getSelectedLocation();
        ArrayList<DetailedWeatherForecastSample> samples = Services.get().getWeatherForecastService().getDetailedForecast(location);


        for(DetailedWeatherForecastSample sample: samples) {
            {
                TextView time = new TextView(this);
                time.setTextAppearance(R.style.table_data);
                time.setPadding(10, 5, 5, 10);
                SimpleDateFormat sdf = new SimpleDateFormat("E kk");
                time.setText(sdf.format(sample.timeStamp.getTime()) + ":00");
                time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                timeRow.addView(time);
            }
            {
                ImageView icon = new ImageView(this);
                TableRow.LayoutParams lparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                icon.setImageDrawable(ContextCompat.getDrawable(this, WeatherIcons.getIconId(sample.weatherType)));
                icon.setAdjustViewBounds(true);
                icon.setMaxWidth(200);
                icon.setMaxHeight(200);
                typeRow.addView(icon);
            }
            {
                TextView precipitationText = new TextView(this);
                precipitationText.setTextAppearance(R.style.table_data);
                precipitationText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                precipitationText.setPadding(10, 5, 5, 10);
                precipitationText.setText(String.valueOf((int) sample.precipitationProbability) + "%");
                precipRow.addView(precipitationText);
            }
            {
                TextView temperatureText = new TextView(this);
                temperatureText.setTextAppearance(R.style.table_data);
                temperatureText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                temperatureText.setPadding(10, 5, 5, 10);
                temperatureText.setText(String.format(getString(R.string.n_degrees_c), (int) sample.temperature_celsius));
                tempRow.addView(temperatureText);
            }
            {
                TextView uvText = new TextView(this);
                uvText.setTextAppearance(R.style.table_data);
                uvText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                uvText.setPadding(10, 5, 5, 10);
                uvText.setText(String.valueOf(sample.uvIndex));
                uvRow.addView(uvText);
            }
            {
                ImageView windDirIcon = new ImageView(this);
                windDirIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_wind_up));
                windDirIcon.setRotation(sample.windDirection_degrees);
                windDirIcon.setAdjustViewBounds(true);
                windDirIcon.setMaxWidth(200);
                windDirIcon.setMaxHeight(200);
                windDirRow.addView(windDirIcon);
            }
            {
                TextView windSpeedText = new TextView(this);
                windSpeedText.setTextAppearance(R.style.table_data);
                windSpeedText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                windSpeedText.setPadding(10, 5, 5, 10);
                windSpeedText.setText(String.format(getString(R.string.n_mph), (int) sample.windSpeed_mph));
                windSpeedRow.addView(windSpeedText);
            }
        }
    }
}
