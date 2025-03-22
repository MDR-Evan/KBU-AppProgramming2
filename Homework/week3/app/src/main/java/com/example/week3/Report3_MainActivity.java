package com.example.week3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Report3_MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report3_main);

        EditText ET = findViewById(R.id.R3_ET);
        TextView TV = findViewById(R.id.R3_TV);
        WebView WV = findViewById(R.id.R3_WV);
        WV.getSettings().setJavaScriptEnabled(true);
        WV.setWebViewClient(new WebViewClient());

        Button B1 = findViewById(R.id.R3_B1);
        Button B2 = findViewById(R.id.R3_B2);

        String page = ET.getText().toString();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WV.setVisibility(View.INVISIBLE);
                TV.setVisibility(View.VISIBLE);

                TV.setText(StrictModeCon.SM_Text(Report3_MainActivity.this, page));
                TV.setBackgroundColor(Color.parseColor("#4242ff"));
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TV.setVisibility(View.INVISIBLE);
                WV.setVisibility(View.VISIBLE);

                WV.loadUrl(page);
            }
        });
    }
}