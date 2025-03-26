package com.example.week3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageThread extends Thread{
    private Bitmap bitmap;
    private String[] image;
    private Context context;
    private Handler handler;

    public ImageThread(Context context, String[] image) {
        this.context = context;
        this.handler = new Handler();
        this.image = image;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(image[2]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(4000);

                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

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

    public Bitmap getResult() {
        return bitmap;
    }
}
