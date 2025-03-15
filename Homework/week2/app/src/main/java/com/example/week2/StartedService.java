package com.example.week2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class StartedService extends AppCompatActivity {
    private MyApplication application;
    private TextView textView;
    private boolean isServiceRunning = false;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Bundle bundle = msg.getData();
                List<Integer> numbers = bundle.getIntegerArrayList("lottoNumbers");
                textView.setText("Lotto 번호: " + numbers.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        application = (MyApplication) getApplication();
        textView = findViewById(R.id.lottoTextView);
        Button lottoButton = findViewById(R.id.lottoButton);

        Intent intent = new Intent(this, LottoStartedService.class);
        Messenger messenger = new Messenger(handler);

        lottoButton.setOnClickListener(view -> {
            intent.putExtra("Messenger", messenger);
            startService(intent);
        });
    }
}
