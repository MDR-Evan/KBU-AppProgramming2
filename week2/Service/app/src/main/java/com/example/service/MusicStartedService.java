package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.Toast;

public class MusicStartedService extends Service {
    private MyApplication myApplication;
    private MediaPlayer player;

    public MusicStartedService() {
    }

    @Override
    public void onCreate() {
        myApplication = (MyApplication) getApplication();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myApplication.setServiced(true);
        myApplication.getButton().setText("Music Service Stop");
        Messenger messenger = intent.getParcelableExtra("Messenger");
        String Title = intent.getStringExtra("Title");
        Toast.makeText(this, "음악 서비스 시작\n제목: " + Title, Toast.LENGTH_SHORT).show();
        player = MediaPlayer.create(this, R.raw.davichi);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });

        MyThread thread = new MyThread(myApplication.getTextView(), player, player.getDuration());
        thread.run();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        player.stop();
        myApplication.setServiced(false);
        myApplication.getButton().setText("Music Started");
        super.onDestroy();
    }
}