package com.example.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownThread extends Thread{
    private Activity activity;
    private MyApplication application;
    private Handler handler;
    private StringBuffer buffer;

    public DownThread(Activity activity) {
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
        handler = new Handler();
        buffer = new StringBuffer();
    }

    @Override
    public void run() {
        try {
            URL url = new URL(application.getPage());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(4000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line + '\n');
                reader.close();
                streamReader.close();
                inputStream.close();
                connection.disconnect();
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "연결되지 않았습니다", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getResult(){
        return buffer.toString();
    }
}
