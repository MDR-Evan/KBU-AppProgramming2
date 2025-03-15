package com.example.week1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.Toast;

public class MusicStartedService extends Service {
    private MediaPlayer player;
    private MyApplication application;

    public MusicStartedService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        application = (MyApplication) getApplication();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        application.setServiced(true);
        application.getButton().setText("Music Service 중지");
        Messenger messenger = intent.getParcelableExtra("Messenger");
        String title = intent.getParcelableExtra("Title");
        boolean loop = intent.getBooleanExtra("loop", true);
        Toast.makeText(this, "Music Service가 시작되었습니다.\n노래 제목 : " + title, Toast.LENGTH_SHORT).show();
        player = MediaPlayer.create(this, R.raw.davichi);
        player.setLooping(loop);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });
        player.start();

        MyThread thread = new MyThread(player, player.getDuration(), messenger);
        thread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        application.setServiced(false);
        application.getButton().setText("Music Service 시작");
        Toast.makeText(this, "Music Service가 종료되었습니다.", Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }
}