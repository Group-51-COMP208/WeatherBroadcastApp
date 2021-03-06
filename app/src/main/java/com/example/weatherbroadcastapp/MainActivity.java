package com.example.weatherbroadcastapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.lib.ApiException;
import com.example.lib.DetailedWeatherForecastSample;
import com.example.lib.Location;
import com.example.lib.LocationService;
import com.example.lib.Utilities;
import com.example.lib.WeatherForecastService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is not best practice. It enables us to run http calls on the main thread, which
        // will cause the UI to freeze. Time allowing (which it is not), we should run http
        // calls on a separate thread with an Async Task.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        Button toMapViewButton = findViewById(R.id.button_toMapView);
        toMapViewButton.setOnClickListener(this);

        Button detailedForecastButton = findViewById(R.id.button_detailedForcast);
        detailedForecastButton.setOnClickListener(this);

        Button longTermForecastButton = findViewById(R.id.button_toLongTermForecast);
        longTermForecastButton.setOnClickListener(this);

        weatherService  = Services.get().getWeatherForecastService();
        locationService = Services.get().getLocationService();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            for (Location location : weatherService.getAvailableLocations()) {
                recognizedLocations.add(location.getDisplayName());
            }
        }
        catch(ApiException e) {
            handleApiException(e);
        }

        ArrayAdapter<String> adapter = new AutoCompleteFavouriteLocationAdapter(this,
                android.R.layout.simple_dropdown_item_1line, recognizedLocations);

        locationSearchView = findViewById(R.id.autoCompleteTextView_location);
        locationSearchView.setAdapter(adapter);
        locationSearchView.setOnEditorActionListener(this);

        initializeLocation();
        updateWeatherInfo();
    }

    @Override
    protected void onStop () {
        super.onStop();
        locationSearchView.clearFocus();
    }

    private void initializeLocation() {
        // Default location set to London if user doesn't want to share their location
        // (they can always set it manually if they like)
        try {
            locationService.setSelectedLocation(weatherService.getLocationByName("London"));
        }
        catch(ApiException e) {
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.show(getSupportFragmentManager(), "Exit dialog");
        }

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            setLocationToActual();
        }
    }

    private void handleApiException(ApiException e) {
        System.err.println("Oh dear. ApiException: " + e.getMessage());
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(), "Exit dialog");
    }

    private void setLocationToActual() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<android.location.Location> task) {
                    android.location.Location location = task.getResult();
                    if(location != null) {
                        try {
                            Location newLocation = Utilities.findClosestLocation(weatherService.getAvailableLocations(), location.getLatitude(), location.getLongitude());
                            if (newLocation != null) {
                                locationService.setSelectedLocation(newLocation);
                            } else {
                                System.out.println("ERROR. Could not find closest location for some reason.");
                            }
                            updateWeatherInfo();
                        } catch (ApiException e) {
                            handleApiException(e);
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        for(int i = 0; i < permissions.length; ++i) {
            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)  &&  grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                setLocationToActual();
            }
        }
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
        else if(v.getId() == R.id.button_toLongTermForecast) {
            Intent longTermForecastIntent = new Intent(this, LongTermForecastActivity.class);
            startActivity(longTermForecastIntent);
        }
    }


    public void updateWeatherInfo() {
        TextView textView_currentLocation = findViewById(R.id.textView_currentLocation);
        TextView textView_currentTemperature = findViewById(R.id.textView_currentTemperature);
        TextView textView_currentWindSpeed = findViewById(R.id.textView_currentWindSpeed);
        ImageView imageView_currentWindDirection = findViewById(R.id.imageView_currentWindDirection);
        ImageView imageView_weatherIcon = findViewById(R.id.imageView_weatherIcon);
        Location selectedLocation = locationService.getSelectedLocation();
        if(selectedLocation != null){
            textView_currentLocation.setText(selectedLocation.getDisplayName());
            try {
                DetailedWeatherForecastSample sample = Services.get().getWeatherForecastService().getDetailedForecast(locationService.getSelectedLocation()).get(0);
                textView_currentTemperature.setText(String.format(getString(R.string.n_degrees_c), (int) sample.temperature_celsius));
                textView_currentWindSpeed.setText(String.valueOf((int) sample.windSpeed_mph));
                imageView_weatherIcon.setImageResource(WeatherIcons.getIconId(sample.weatherType));

                imageView_currentWindDirection.setRotation(sample.windDirection_degrees);

                ImageView[] dailyIcons = {
                        findViewById(R.id.weatherIcon_day1),
                        findViewById(R.id.weatherIcon_day2),
                        findViewById(R.id.weatherIcon_day3)
                };
                ArrayList<DetailedWeatherForecastSample> dailyForecast = Services.get().getWeatherForecastService().getDailyForecast(locationService.getSelectedLocation());
                for (int i = 1; i < dailyForecast.size() && i < 4; ++i) {
                    dailyIcons[i - 1].setImageResource(WeatherIcons.getIconId(dailyForecast.get(i).weatherType));
                }
            }
            catch(ApiException e) {
                handleApiException(e);
            }
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            try {
                Location selectedLocation = weatherService.getLocationByName(v.getText().toString());
                if (selectedLocation != null) {
                    locationService.setSelectedLocation(selectedLocation);
                    InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                    updateWeatherInfo();
                    locationService.registerLocationUsage(selectedLocation.getDisplayName());
                } else {
                    Toast.makeText(MainActivity.this,
                            String.format(getString(R.string.unrecognized_location_msg), v.getText()),
                            Toast.LENGTH_SHORT).show();
                }

                return true;
            }
            catch (ApiException e) {
                handleApiException(e);
            }
        }
        return false;
    }

    private FusedLocationProviderClient fusedLocationProviderClient;
    private WeatherForecastService weatherService;
    private LocationService locationService;
    private ArrayList<String> recognizedLocations = new ArrayList<String>();
    private AutoCompleteTextView locationSearchView;
}
