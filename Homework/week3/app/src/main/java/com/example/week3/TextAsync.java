package com.example.week3;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextAsync extends AsyncTask<String, String, String> {
    private Context context;
    private TextView TV;

    public TextAsync(Context context, TextView TV) {
        this.context = context;
        this.TV = TV;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();

        try {
            URL url = new URL(strings[0]);
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
            publishProgress(e.getMessage());
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        TV.setTextColor(Color.RED);
        TV.setText(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
    }
}
