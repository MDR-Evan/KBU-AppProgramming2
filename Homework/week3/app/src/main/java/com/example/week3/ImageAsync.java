package com.example.week3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAsync extends AsyncTask<String, String, Bitmap>{
    private Context context;
    private String[] image;

    public ImageAsync(Context context, String[] image) {
        this.context = context;
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(image[1]);
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
            publishProgress(e.getMessage());
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView IV = ((AppCompatActivity) context).findViewById(R.id.R1_IV);
        IV.setImageBitmap(bitmap);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
    }
}
