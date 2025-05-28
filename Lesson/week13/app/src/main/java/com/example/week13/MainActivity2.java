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

public class MainActivity2 extends AppCompatActivity {
    private boolean color = false;
    private boolean logo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText et = findViewById(R.id.ET);
        ImageView iv = findViewById(R.id.IV);

        QRcodeHandler handler = new QRcodeHandler(this, iv);

        Button bt = findViewById(R.id.BT);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    handler.encoding(color, logo, text);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int check = item.getItemId();
        if (check == R.id.item1) {
            if (!color) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
            color = !color;
        } else {
            if (!logo) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
            logo = !logo;
        }
        return true;
    }
}