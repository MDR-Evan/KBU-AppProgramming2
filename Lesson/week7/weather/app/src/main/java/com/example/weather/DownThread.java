package com.example.weather;

import android.content.Context;
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
    private Context context;
    private String page;
    private Handler handler;
    private StringBuffer buffer;

    public DownThread(Context context, String page) {
        this.buffer = new StringBuffer();
        this.context = context;
        this.handler = new Handler();
        this.page = page;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(4000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "연결 불량", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getResult() {
        return buffer.toString();
    }
}
