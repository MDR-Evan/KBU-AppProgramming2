package com.example.week2;

import android.app.Application;
import android.widget.Button;
import android.widget.TextView;

public class MyApplication extends Application {
    private Button button;
    private TextView textView;

    public MyApplication() {
        super();
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
