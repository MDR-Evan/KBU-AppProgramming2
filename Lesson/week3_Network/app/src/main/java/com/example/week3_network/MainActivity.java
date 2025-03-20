package com.example.week3_network;

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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        EditText editText = findViewById(R.id.editText);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        TextView textView = findViewById(R.id.textview);
        ImageView imageView = findViewById(R.id.imageView);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

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

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                String page = editText.getText().toString();
//                String html = getHtml(page);
                if (type == 1) {
                    HTMLDownThread thread = new HTMLDownThread(page, MainActivity.this);
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
                    HTMLDownRunnable runnable = new HTMLDownRunnable(page, MainActivity.this);
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
                    HTMLDownAsyncTask asyncTask = new HTMLDownAsyncTask(MainActivity.this);
                    try {
                        String result = asyncTask.execute(page).get();
                        textView.setText(result);
                        textView.setTextColor(Color.RED);
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);

                if (type == 1) {
                    String page = "http://manage.kbu.ac.kr/resources/_Plugin/namoeditor/binary/images/000004/02.jpg";
                    ImageDownThread thread = new ImageDownThread(MainActivity.this, page);
                    thread.start();
                    try {
                        thread.join();
                        Bitmap bitmap = thread.getResult();
                        imageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (type == 2) {
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
                    String page = "https://info.kbu.ac.kr/attach/IMAGE/Board/72/2022/10/ep4bD8kr3h4s9JVD.JPG";
                    ImageDownAsyncTask asyncTask = new ImageDownAsyncTask(MainActivity.this);
                    try {
                        Bitmap bitmap = asyncTask.execute(page).get();
                        imageView.setImageBitmap(bitmap);
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private String getHtml(String page) {
        return page;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            type = 1;
        } else if (item.getItemId() == 2) {
            type = 2;
        } else {
            type = 3;
        }
        item.setChecked(true);
        return true;
    }
}