package com.example.week2;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoStartedService extends Service {
    private MyApplication application;

    public LottoStartedService() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        application = (MyApplication) getApplication();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Messenger messenger = intent.getParcelableExtra("Messenger");

        List<Integer> lotto = randomLottoNum();
        Toast.makeText(this, String.format("Lotto 숫자: %s", lotto.toString()), Toast.LENGTH_SHORT).show();

        if (messenger != null) {
            Message message = Message.obtain();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("lottoNumbers", new ArrayList<>(lotto));
            message.setData(bundle);

            try {
                messenger.send(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    private List<Integer> randomLottoNum() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 45; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, 7);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}