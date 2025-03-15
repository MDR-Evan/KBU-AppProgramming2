package com.example.service;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;

public class MyThread {
    private TextView textView;
    private MediaPlayer player;
    private int size;

    public MyThread(TextView textView, MediaPlayer player, int size) {
        this.textView = textView;
        this.player = player;
        this.size = size;
    }

    public void run() {
        if (player.isPlaying()) {
            int current = player.getCurrentPosition();
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(current + "/" + size);
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
