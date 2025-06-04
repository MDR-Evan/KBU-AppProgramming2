package com.example.week14;

import android.hardware.*;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity implements SensorEventListener {
    private TextView tv1, tv2, tv3;
    private Sensor temperature, humidity;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float temperature = 0.0f, humidity = 0.0f;

        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature = sensorEvent.values[0];
            tv1.setText(String.format("온도: %.2f \u2103", temperature));
        } else if (type == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidity = sensorEvent.values[0];
            tv2.setText(String.format("습도: %.2f %", humidity));
        }

        float index = calculate(temperature, humidity);
        String message = mean(index);
        tv3.setText(String.format("불쾌 지수: %.2f (%s)", index, message));
    }

    private String mean(float index) {
        String message;

        if (index <= 68.0f) {
            message = "쾌적함";
        } else if (index <= 70.0f) {
            message = "불쾌를 나타냄";
        } else if (index <= 75.0f) {
            message = "10% 정도 쿨쾌를 나타냄";
        } else if (index <= 80.0f) {
            message = "50% 정도 불쾌를 나타냄";
        } else if (index <= 83.0f) {
            message = "전원 불쾌";
        } else {
            message = "매우 불쾌함";
        }

        return message;
    }

    private float calculate(float temperature, float humidity) {
        float index = (((9.0f / 5.0f) * temperature) - 0.55f) * (1.0f - humidity/100.0f) * (9.0f / 5.0f * temperature - 26.0f) + 32.0f;
        return index;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}