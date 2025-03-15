package com.example.week2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startedButton = findViewById(R.id.buttonStartedService);
        Button boundedButton = findViewById(R.id.buttonBoundedService);
        Button intentButton = findViewById(R.id.buttonIntentService);
        Button foregroundButton = findViewById(R.id.buttonForeGroundService);
        Button remoteButton = findViewById(R.id.buttonRemoteService);

        startedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StartedService.class));
            }
        });
    }
}