package com.example.weatherbroadcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lib.Services;
import com.example.lib.TextualForecast;
import com.example.lib.Utilities;

import java.text.SimpleDateFormat;

public class LongTermForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_term_forecast);

        TextualForecast forecast = Services.get().getWeatherForecastService().getLongTermForecast();
        TextView periodText = findViewById(R.id.textView_longTermForecastPeriod);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        periodText.setText(String.format(getString(R.string.time_period),
                simpleDateFormat.format(forecast.startTime.getTime()),
                simpleDateFormat.format(Utilities.addTime(forecast.startTime, forecast.duration).getTime())));

        TextView textBody   = findViewById(R.id.textView_longTermForecastBody);
        textBody.setText(forecast.text);
    }
}