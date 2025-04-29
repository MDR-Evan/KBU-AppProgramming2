package com.example.week8;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextThread extends Thread {

    public interface OnResultListener {
        void onResult(String result);
    }

    private StringBuffer buffer;
    private String page;
    private Context context;
    private Handler handler;
    private OnResultListener listener;

    public TextThread(String page, Context context, Handler handler, OnResultListener listener) {
        this.buffer = new StringBuffer();
        this.page = page;
        this.context = context;
        this.handler = handler;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(4000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append('\n');
                }

                reader.close();
                streamReader.close();
                inputStream.close();
                connection.disconnect();

                handler.post(() -> {
                    if (listener != null) {
                        listener.onResult(buffer.toString());
                    }
                });
            }
        } catch (IOException e) {
            handler.post(() -> Toast.makeText(context, "네트워크 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
