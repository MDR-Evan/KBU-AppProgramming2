package com.example.week1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartedActivity extends AppCompatActivity {
    private MyApplication application;
    private ProgressBar progressBar;
    private TextView textView2;
    private boolean loop = false;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (application.isServiced()) {
                if(msg.what == 0){
                    Bundle bundle = msg.getData();
                    int current = bundle.getInt("current");
                    int size = bundle.getInt("size");
                    progressBar.setMax(size);
                    progressBar.setProgress(current);
                    String time = application.getTime(current) + "/" + application.getTime(size);
                    textView2.setText(time);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);

        application = (MyApplication) getApplication();
        TextView textView = findViewById(R.id.textView1);
        textView.setText("음악 Service(Started Service)");
        progressBar = findViewById(R.id.progressBar);
        textView2 = findViewById(R.id.textView2);

        Intent intent = new Intent(this, MusicStartedService.class);
        Messenger messenger = new Messenger(handler);
        application.setButton(findViewById(R.id.button));
        application.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!application.isServiced()) {
                    intent.putExtra("Messenger", messenger);
                    intent.putExtra("Title", "헤어졌다 만났다");
                    intent.putExtra("loop", loop);
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.isCheckable()) {
            loop = false;
        } else {
            loop = true;
        }
        item.setChecked(loop);
        return true;
    }


}