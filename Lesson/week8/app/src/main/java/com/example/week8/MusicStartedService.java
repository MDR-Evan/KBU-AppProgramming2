package com.example.week8;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicStartedService extends Service {
    private MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스가 만들어질 때 한 번만 MediaPlayer 생성
        player = MediaPlayer.create(this, R.raw.davichi);
        player.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 재생 중이 아니면 시작
        if (player != null && !player.isPlaying()) {
            player.start();
            Toast.makeText(this, "음악 서비스 시작", Toast.LENGTH_SHORT).show();
        }
        // START_STICKY로 서비스가 강제 종료돼도 재시작
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // 서비스 종료 시 반드시 stop + release
        if (player != null) {
            if (player.isPlaying()) player.stop();
            player.release();
            player = null;
        }
        Toast.makeText(this, "음악 서비스 종료", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

