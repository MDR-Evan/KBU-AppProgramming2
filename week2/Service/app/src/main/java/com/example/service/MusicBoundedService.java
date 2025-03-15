package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.Toast;

public class MusicBoundedService extends Service {
    private MyApplication application;
    private MediaPlayer player;


    public MusicBoundedService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = (MyApplication) getApplication();
    }

    @Override
    public IBinder onBind(Intent intent) {
        application.setServiced(true);
        application.getButton().setText("Music Service 중지");
        Messenger messenger = intent.getParcelableExtra("Messenger");
        String title = intent.getStringExtra("title");
        Toast.makeText(getBaseContext(), "시작 제목" + title, Toast.LENGTH_SHORT).show();

        player = MediaPlayer.create(this, R.raw.davichi);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                application.getButton().performClick();
            }
        });

        MyThread thread = new MyThread(application.getTextView(), player, player.getDuration());
        thread.run();

        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        application.setServiced(false);
        application.getButton().setText("Music Service 시작");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
    }
}