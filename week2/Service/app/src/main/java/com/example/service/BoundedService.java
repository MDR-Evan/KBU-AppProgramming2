package com.example.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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

public class BoundedService extends AppCompatActivity {
    private MyApplication application;
    private ProgressBar processBar;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (application.isServiced()) {
                if(msg.what == 0) {
                    Bundle bundle = msg.getData();
                    int current = bundle.getInt("current");
                    int size = bundle.getInt("size");
                    String temp = application.getTime(current) + "/" + application.getTime(size);
                    processBar.setMax(size);
                    processBar.setProgress(current);
                }
            }
        }
    };

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        application = (MyApplication) getApplication();
        TextView textView = findViewById(R.id.textView1);
        textView.setText("Music Service(Bounded Service)");
        processBar = findViewById(R.id.progress);
        application.setTextView(findViewById(R.id.textView2));


        Intent intent = new Intent(BoundedService.this, MusicBoundedService.class);
        Messenger messenger = new Messenger(handler);
        application.setButton(findViewById(R.id.button));
        application.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!application.isServiced()) {
                    intent.putExtra("Messenger", messenger);
                    intent.putExtra("Title", "헤어졌다 만났다.");
                    bindService(intent, connection, BIND_AUTO_CREATE);
                } else {
                    unbindService(connection);
                }
            }
        });
    }
}