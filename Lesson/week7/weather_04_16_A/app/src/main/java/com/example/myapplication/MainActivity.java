package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyApplication application;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "xptmx", Toast.LENGTH_SHORT).show();
                DownThread thread = new DownThread(MainActivity.this);
                thread.start();
                try {
                    thread.join();
                    application.setXml(thread.getResult());
                    Toast.makeText(getBaseContext(), application.getXml(), Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                ArrayList<Weather> weathers = null;
                if (application.getType() == R.id.item1) {
                    PULLParser parser = new PULLParser(MainActivity.this, textView);
                    weathers = parser.parsing();
                } else if (application.getType() == R.id.item2) {
                    weathers = null;
                } else {
                    weathers = null;
                }
                WeatherAdapter adapter = new WeatherAdapter(MainActivity.this,0, weathers);
                listView.setAdapter(adapter);
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
        textView.setText("");
        listView.setAdapter(null);
        return true;
    }
}