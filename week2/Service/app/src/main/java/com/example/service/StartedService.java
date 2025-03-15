package com.example.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartedService extends AppCompatActivity {
    private MyApplication application;
    private ProgressBar progressBar;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (application.isServiced()) {
                if (msg.what == 0) {
                    Bundle bundle = msg.getData();
                    int current = bundle.getInt("current");
                    int size = bundle.getInt("size");
                    String temp = application.getTime(current) + "/" + application.getTime(size);
//                    textView2.setText(temp);
                    progressBar.setMax(size);
                    progressBar.setProgress(current);
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        application = (MyApplication) getApplication();
        TextView textView1 = findViewById(R.id.textView1);
        textView1.setText("Music Service(Started)");
        progressBar = findViewById(R.id.progress);
        application.setTextView(findViewById(R.id.textView2));
        Intent intent = new Intent(this, MusicStartedService.class);
        Messenger messenger = new Messenger(handler);

        application.setButton(findViewById(R.id.button));
        application.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!application.isServiced()) {
                    intent.putExtra("Messenger", messenger);
                    intent.putExtra("title", "헤어졌다 만났다.");

                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });
    }
}