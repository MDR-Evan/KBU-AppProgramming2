package com.example.week3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Report2_MainActivity extends AppCompatActivity {
    private int type = 1;
    private String textFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2_main);

        Button B1 = findViewById(R.id.R2_B1);
        Button B2 = findViewById(R.id.R2_B2);
        TextView TV = findViewById(R.id.R2_TV);
        WebView WV = findViewById(R.id.R2_WV);
        WV.getSettings().setJavaScriptEnabled(true);

        String page = "http://www.soen.kr/html5/html/htmlonly.html";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TV.setVisibility(View.VISIBLE);
                WV.setVisibility(View.INVISIBLE);

                if (type == 1) {
                    textFile = StrictModeCon.SM_Text(Report2_MainActivity.this, page);
                    TV.setTextColor(Color.BLACK);
                    TV.setText(textFile);
                } else if (type == 2) {
                    TextAsync async = new TextAsync(Report2_MainActivity.this, TV);
                    async.execute(page);
                } else if (type == 3) {
                    TextThread thread = new TextThread(page, Report2_MainActivity.this);
                    thread.run();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            String result = thread.getResult();
                            TV.setTextColor(Color.BLUE);
                            TV.setText(result);
                        }
                    });
                }
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TV.setVisibility(View.INVISIBLE);
                WV.setVisibility(View.VISIBLE);

                WV.loadUrl(page);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report2_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            type = 1;
        } else if (item.getItemId() == R.id.item2){
            type = 2;
        } else {
            type = 3;
        }

        item.setChecked(true);
        return true;
    }
}