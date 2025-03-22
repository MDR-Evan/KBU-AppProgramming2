package com.example.week3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class INDEX extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button Report1_button = findViewById(R.id.Report1_Button);
        Button Report2_button = findViewById(R.id.Report2_Button);
        Button Report3_button = findViewById(R.id.Report3_Button);

        Report1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(INDEX.this, Report1_MainActivity.class);
                startActivity(intent);
            }
        });

        Report2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(INDEX.this, Report2_MainActivity.class);
                startActivity(intent);
            }
        });

        Report3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(INDEX.this, Report3_MainActivity.class);
                startActivity(intent);
            }
        });
    }
}