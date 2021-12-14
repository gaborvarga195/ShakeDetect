package com.example.shakedetect;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shakedetect.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private ActivityMainBinding binding;
    private int maximum = 0;
    private double prev = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.seekBar.setMax(20);
        binding.seekBar.setProgress(0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        binding.seekBar.setProgress(0);
        double avg = Math.sqrt(Math.pow(event.values[0],2) + Math.pow(event.values[1],2) + Math.pow(event.values[2],2));
        double value = Math.abs(avg - prev);
        prev = avg;
        binding.seekBar.setProgress((int)value);
        if(maximum < value){
            maximum = (int)value;
        }
        binding.max.setText("" + maximum);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}