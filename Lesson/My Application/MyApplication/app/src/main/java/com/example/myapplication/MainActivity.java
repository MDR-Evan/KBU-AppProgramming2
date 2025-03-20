package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private  int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      //  StrictMode.setThreadPolicy(policy);

        EditText editText = findViewById(R.id.editText);
        TextView textView = findViewById(R.id.textView);
        WebView webView= findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        ImageView imageView = findViewById(R.id.imageView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                String page = editText.getText().toString();
                webView.loadUrl(page);
            }
        });
        
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                String page = editText.getText().toString();
                if (type == 1) {
                    HtmlDownThread thread = new HtmlDownThread(page, MainActivity.this);
                    thread.start();
                    try {
                        thread.join();
                        String html = thread.getResult();
                        textView.setText(html);
                        textView.setTextColor(Color.BLACK);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (type == 2) {
                    Toast.makeText(MainActivity.this, "Runnable", Toast.LENGTH_SHORT).show();
                     HtmlDownRunnable runnable =  new HtmlDownRunnable(page, MainActivity.this);
                     Thread thread = new Thread(runnable);
                     thread.start();
                    try {
                        thread.join();
                        String html = runnable.getResult();
                        textView.setText(html);
                        textView.setTextColor(Color.BLUE);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "AsyncTask", Toast.LENGTH_SHORT).show();
                    HtmlDownAsync async = new HtmlDownAsync(MainActivity.this);
                    try {
                        String result = async.execute(page).get();
                        textView.setText(result);
                        textView.setTextColor(Color.RED);
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.INVISIBLE);
                if (type == 1) {
                    String page = "http://manage.kbu.ac.kr/resources/_Plugin/namoeditor/binary/images/000004/02.jpg";
                    ImageDownThread thread = new ImageDownThread(MainActivity.this, page);
                    thread.start();
                    try {
                        thread.join();
                        Bitmap bitmap = thread.getResult();
                        imageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (type == 2) {
                    Toast.makeText(MainActivity.this, "Runnable", Toast.LENGTH_SHORT).show();
                    String page = "http://manage.kbu.ac.kr/resources/_Plugin/namoeditor/binary/images/000004/03.png";
                    ImageDownRunnable runnable = new ImageDownRunnable(MainActivity.this, page);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    try {
                        thread.join();
                        Bitmap bitmap = runnable.getResult();
                        imageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Asynctask", Toast.LENGTH_SHORT).show();
                    String page = "https://info.kbu.ac.kr/attach/IMAGE/Board/72/2022/10/ep4bD8kr3h4s9JVD.JPG";
                    ImageDownAsync async = new ImageDownAsync(MainActivity.this);
                    try {
                        Bitmap bitmap = async.execute(page).get();
                        imageView.setImageBitmap(bitmap);
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1 :
                type = 1;
                break;
            case R.id.item2 :
                type = 2;
                break;
            case R.id.item3 :
                type = 3;
                break;
        }
        item.setChecked(true);
        return true;
    }
}