package com.example.week13;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int type = 1;
    private boolean color = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et = findViewById(R.id.ET);
        ImageView iv = findViewById(R.id.IV);

        BarcodeHandler handler = new BarcodeHandler(this, iv);

        Button bt = findViewById(R.id.BT);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                if (text.matches("\\d+")) {
                    handler.encoding(type, color, text);
                } else {
                    Toast.makeText(MainActivity.this, "입력해주세요.", Toast.LENGTH_SHORT).show();
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
        int check = item.getItemId();
        if (check == R.id.item1) {
            type = 1;
            item.setChecked(true);
        } else if (check == R.id.item2) {
            type = 2;
            item.setChecked(true);
        } else {
            if (!color) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
            color = !color;
        }
        return true;
    }
}