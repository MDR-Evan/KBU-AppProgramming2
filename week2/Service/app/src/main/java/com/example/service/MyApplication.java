package com.example.service;

import android.app.Application;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MyApplication extends Application {
    private boolean isServiced = false;
    private int progress = 0;
    private int maxProgress = 0;
    private Button button;
    private TextView textView;


    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public boolean isServiced() {
        return isServiced;
    }

    public void setServiced(boolean serviced) {
        isServiced = serviced;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getTime(int time) {
        int second = time / 1000;
        int minute = second / 60;
        second %= 60;
        return String.format(Locale.KOREA, "%02d:%02d", minute, second);
    }
}
