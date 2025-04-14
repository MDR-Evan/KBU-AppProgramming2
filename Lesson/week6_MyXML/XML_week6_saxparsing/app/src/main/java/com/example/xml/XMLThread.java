package com.example.xml;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLThread extends Thread {
    private Context context;
    private String page;
    private Handler handler;
    private StringBuilder stringBuilder;

    public XMLThread(Context context, String page) {
        this.context = context;
        this.page = page;
        this.handler = new Handler();
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void run() {
        try {
            URL url = new URL(page);
            InputStream inputStream = url.openStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getResult() {
        return stringBuilder.toString();
    }
}
