package com.example.silviorodrigues.sensor_research;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;

    private Sensor mSensorLight;
    private Sensor mSensorAmbientTemperature;
    private Sensor mSensorRelativeHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorAmbientTemperature;
    private TextView mTextSensorRelativeHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorAmbientTemperature = (TextView) findViewById(R.id.label_ambient_temperature);
        mTextSensorRelativeHumidity = (TextView) findViewById(R.id.label_relative_humidity);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorRelativeHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorLight == null) { mTextSensorLight.setText(sensor_error); }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorAmbientTemperature != null) {
            mSensorManager.registerListener(this, mSensorAmbientTemperature, SensorManager.SENSOR_DELAY_FASTEST);
        }

        if (mSensorRelativeHumidity != null) {
            mSensorManager.registerListener(this, mSensorRelativeHumidity, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();

        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbientTemperature.setText(getResources().getString(R.string.label_ambient_temperature, currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorRelativeHumidity.setText(getResources().getString(R.string.label_relative_humidity, currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

}
