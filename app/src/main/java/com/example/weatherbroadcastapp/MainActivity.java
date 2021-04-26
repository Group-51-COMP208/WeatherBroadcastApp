package com.example.weatherbroadcastapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.lib.DetailedWeatherForecastSample;
import com.example.lib.Location;
import com.example.lib.Services;
import com.example.lib.SimpleWeatherForecastSample;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Duration;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toMapViewButton = findViewById(R.id.button_toMapView);
        toMapViewButton.setOnClickListener(this);

        Button detailedForecastButton = findViewById(R.id.button_detailedForcast);
        detailedForecastButton.setOnClickListener(this);

        Button simpleForecastButton = findViewById(R.id.button_toSimpleForecast);
        simpleForecastButton.setOnClickListener(this);

        for(Location location: Services.get().getWeatherForecastService().getAvailableLocations()) {
            System.out.println(location.getDisplayName());
        }

        // TODO: get user's actual current location if possible, as a default?
        currentLocation = Services.get().getFavouriteLocationService().getSelectedLocation();
        updateWeatherInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_toMapView) {
            Intent mapActivityIntent = new Intent(this, MapActivity.class);
            startActivity(mapActivityIntent);
        }
        else if(v.getId() == R.id.button_detailedForcast) {
            Intent detailedForecastIntent = new Intent(this, DetailedForecastActivity.class);
            startActivity(detailedForecastIntent);
        }
        else if(v.getId() == R.id.button_toSimpleForecast) {
            Intent simpleForecastIntent = new Intent(this, SimpleForecastActivity.class);
            startActivity(simpleForecastIntent);
        }
    }


    public void updateWeatherInfo() {
        TextView textView_currentLocation = findViewById(R.id.textView_currentLocation);
        TextView textView_currentTemperature = findViewById(R.id.textView_currentTemperature);
        TextView textView_currentWindSpeed = findViewById(R.id.textView_currentWindSpeed);
        ImageView imageView_currentWindDirection = findViewById(R.id.imageView_currentWindDirection);
        ImageView imageView_weatherIcon = findViewById(R.id.imageView_weatherIcon);
        textView_currentLocation.setText(currentLocation.getDisplayName());

        DetailedWeatherForecastSample sample = Services.get().getWeatherForecastService().getDetailedForecast(Calendar.getInstance(),
                                            Duration.ofHours(1), 1, currentLocation).get(0);
        textView_currentTemperature.setText(String.format(getString(R.string.n_degrees_c), (int)sample.temperature_celsius));
        textView_currentWindSpeed.setText(String.valueOf((int)sample.windSpeed_mph));
        imageView_weatherIcon.setImageResource(WeatherIcons.getIconId(sample.weatherType));

        imageView_currentWindDirection.setRotation(sample.windDirection_degrees);
    }

    private Location currentLocation = null;
}
