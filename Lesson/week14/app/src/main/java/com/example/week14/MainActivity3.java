package com.example.week14;

import android.hardware.*;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity implements SensorEventListener {
    private TextView tv1, tv2, tv3, tv4;
    private Sensor temperature, humidity, pressure;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float temperature = 0.0f, humidity = 0.0f, pressure = 0.0f;
        String sensortype = "";

        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature = sensorEvent.values[0];
            tv1.setText(String.format("온도: %.2f \u2103", temperature));
            sensortype = "temperature";
        } else if (type == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidity = sensorEvent.values[0];
            tv2.setText(String.format("습도: %.2f %", humidity));
            sensortype = "humidity";
        } else if (type == Sensor.TYPE_PRESSURE) {
            pressure = sensorEvent.values[0];
            tv3.setText(String.format("기압: %.2f hPa", pressure));
            sensortype = "pressure";
        }

        if (!sensortype.isEmpty()) {
            String forecast = forecast(temperature, humidity, pressure);
            tv4.setText(String.format("기상 예측: %s", forecast));
        }
    }

    private String forecast(float temperature, float humidity, float pressure) {
        String message;

        if (pressure > 1013.25f && humidity < 50.0f) {
            message = "기상은 점점 많은 상태가 됩니다";
        } else if (pressure < 1003.25f && humidity > 70.0f) {
            if (temperature < 4.0f) {
                message = "눈이 올수도 있습니다.";
            } else {
                message = "비가 올수도 있습니다.";
            }
        } else {
            message = "구름이 끼고 있습니다.";
        }
        return message;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}