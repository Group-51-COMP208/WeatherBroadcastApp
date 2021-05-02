package com.example.weatherbroadcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lib.ApiException;
import com.example.lib.TextualForecast;

public class LongTermForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_term_forecast);

        try {
            TextualForecast forecast = Services.get().getWeatherForecastService().getLongTermForecast();
            TextView periodText = findViewById(R.id.textView_longTermForecastPeriod);
            periodText.setText(forecast.period);

            TextView textBody = findViewById(R.id.textView_longTermForecastBody);
            textBody.setText(forecast.text);
        }
        catch(ApiException e) {
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.show(getSupportFragmentManager(), "Exit dialog");
        }
    }
}
