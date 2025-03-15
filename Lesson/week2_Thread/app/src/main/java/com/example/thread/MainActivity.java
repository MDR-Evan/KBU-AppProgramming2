package com.example.thread;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private int type = 1;
    private TextView textView;
    MyThread thread;
    Thread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (type == 1) {
            if (thread1 != null && thread1.isAlive()) {
                thread1.interrupt();
            } else {
                thread = new MyThread(textView);
                thread.start();
            }
        } else {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            } else {
                TimerThread timerThread = new TimerThread(textView);
                thread1 = new Thread(timerThread);
                thread1.start();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            type = 1;
        } else {
            type = 2;
        }
        item.setChecked(true);
        onStart();
        return true;
    }
}