package com.example.weatherbroadcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MapActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar  timeSeekBar;
    private TextView timeTextView;
    private MapView  mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        timeSeekBar   = findViewById(R.id.seekBar_currentTime);
        timeTextView  = findViewById(R.id.textView_currentTime);
        mapView       = findViewById(R.id.mapView);

        timeSeekBar.setOnSeekBarChangeListener(this);

        setCurrentTimeText(mapView.getCurrentTime());

//        FrameLayout frameLayout = findViewById(R.id.frameLayout_main);
//        ImageView mapView = findViewById(R.id.imageView_map);
//
//        TextView textView1 = new TextView(this);
//        textView1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT));
//        textView1.setText("programmatically created TextView1");
//        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
//        textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
//        frameLayout.addView(textView1);
    }

    private void setCurrentTimeText(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("E hha");
        timeTextView.setText(sdf.format(cal.getTime()));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float ratio = (float) (progress - seekBar.getMin()) / (float)(seekBar.getMax() - seekBar.getMin());
        mapView.setTimeNormalized(ratio);
        setCurrentTimeText(mapView.getCurrentTime());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // ...
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // ...
    }
}