package com.example.week3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StrictModeCon {
    public static String SM_Text(Context context, String page) {
        StringBuffer buffer = new StringBuffer();

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
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return buffer.toString();
    }

    public static Bitmap SM_Image(Context context, String image) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(4000);
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                inputStream.close();
                connection.disconnect();
            }
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return bitmap;
    }
}
