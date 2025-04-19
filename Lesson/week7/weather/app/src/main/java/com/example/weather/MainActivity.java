package com.example.weather;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ListView lv;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();
        tv = findViewById(R.id.TV);
        lv = findViewById(R.id.LV);
        Button bt = findViewById(R.id.BT);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownThread thread = new DownThread(MainActivity.this, application.getPage());
                thread.start();
                try {
                    thread.join();
                    application.setXml(thread.getResult());
                } catch (InterruptedException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                ArrayList<Weather> weathers = null;
                if (application.getType() == R.id.item1) {
                    PULLParser pullParser = new PULLParser(MainActivity.this, tv);
                    weathers = pullParser.parsing();
                    lv.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                } else if (application.getType() == R.id.item2) {
                    DOMParser domParser = new DOMParser(MainActivity.this, tv);
                    weathers = domParser.parsing();
                    lv.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                } else if (application.getType() == R.id.item3) {
                    
                }
                WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, 0, weathers);
                lv.setAdapter(adapter);
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
        application.setType(item.getItemId());
        item.setChecked(true);
        return true;
    }
}