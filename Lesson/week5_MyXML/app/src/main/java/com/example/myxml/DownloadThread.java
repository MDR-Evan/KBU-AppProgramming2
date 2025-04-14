package com.example.myxml;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadThread extends Thread{
    private Context context;
    private String page;
    private Handler handler;
    private StringBuffer buffer;

    public DownloadThread(Context context, String page) {
        this.context = context;
        this.handler = new Handler();
        this.page = page;
        this.buffer = new StringBuffer();
    }

    @Override
    public void run() {
        try {
            URL url = new URL(page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection != null) {
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setReadTimeout(4000);
                connection.setDoInput(true);
                connection.setDoOutput(false);
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(streamReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + '\n');
                    }
                    reader.close();
                    streamReader.close();
                    inputStream.close();
                    connection.disconnect();
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Network의 연결이 안됨", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
