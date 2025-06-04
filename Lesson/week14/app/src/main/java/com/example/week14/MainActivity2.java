package com.example.week14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {
    private TextView tv1, tv2;
    private Sensor pressure;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        if (type == Sensor.TYPE_PRESSURE) {
            float pressure = sensorEvent.values[0];
            tv1.setText(String.format("기압: %.2f hPa", pressure));
            String forecast = forecast(pressure);
            tv2.setText(String.format("기상 예측: %s", forecast));
        }
    }

    private String forecast(float pressure) {
        String message;
        if (pressure > 1013.25f) {
            message = "기상은 점점 많은 상태가 됩니다";
        } else if (pressure >= 1003.25f) {
            message = "기상은 점점 흐린 상태가 됩니다";
        } else {
            message = "기상은 비가 올수도 있다.";
        }

        return message;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}