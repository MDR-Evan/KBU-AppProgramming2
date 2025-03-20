package com.example.week3_network;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTMLDownRunnable implements Runnable{
    private StringBuffer buffer;
    private String page;
    private Context context;
    private Handler handler;

    public HTMLDownRunnable(String buffer, Context context) {
        this.buffer = new StringBuffer();
        this.context = context;
        this.handler = new Handler();
        this.page = page;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(4000);

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
