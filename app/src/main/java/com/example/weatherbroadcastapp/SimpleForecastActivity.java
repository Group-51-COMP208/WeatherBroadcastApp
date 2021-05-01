package com.example.weatherbroadcastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lib.Location;
import com.example.lib.SimpleWeatherForecastSample;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

public class SimpleForecastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_forecast);

        Location location = Services.get().getLocationService().getSelectedLocation();
        ArrayList<SimpleWeatherForecastSample> samples = Services.get().getWeatherForecastService().getSimpleForecast(
                Calendar.getInstance(), location);

        TableLayout table = findViewById(R.id.tableLayout_simpleForecast);

        for(SimpleWeatherForecastSample sample: samples) {
            TableRow row =  new TableRow(this);
            {
                TextView time = new TextView(this);
                time.setTextAppearance(R.style.table_data);
                time.setPadding(10, 5, 5, 10);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH");
                time.setText(sdf.format(sample.timeStamp.getTime()) + ":00");
                time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(time);
            }
            {
                ImageView icon = new ImageView(this);
                TableRow.LayoutParams lparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                icon.setImageDrawable(ContextCompat.getDrawable(this, WeatherIcons.getIconId(sample.weatherType)));
                icon.setAdjustViewBounds(true);
                icon.setMaxWidth(200);
                icon.setMaxHeight(200);
                row.addView(icon);
            }

            table.addView(row);
        }
    }
}