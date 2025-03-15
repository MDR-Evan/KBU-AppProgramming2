package com.example.thread;

import android.graphics.Color;
import android.widget.TextView;

import java.util.Calendar;

public class TimerThread implements Runnable{
    private TextView textView;

    public TimerThread(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Calendar calendar = Calendar.getInstance();
            String time = String.valueOf(calendar.getTime());
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(time);
                    textView.setTextColor(Color.RED);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
