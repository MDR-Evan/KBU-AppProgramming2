package com.example.week1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MyThread extends Thread{
    private MediaPlayer player;
    private int size;
    private Messenger messenger;

    public MyThread(MediaPlayer player, int size, Messenger messenger) {
        this.player = player;
        this.size = size;
        this.messenger = messenger;
    }

    public void run() {
        while (player.isPlaying()) {
            int current = player.getCurrentPosition();
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putInt("current", current);
            bundle.putInt("size",size);
            message.setData(bundle);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        super.run();
    }
}
