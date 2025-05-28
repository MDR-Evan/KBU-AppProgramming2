package com.example.week12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomDBHelper helper = new CustomDBHelper(this);
        TextView tv = findViewById(R.id.tv);
        EditText et1 = findViewById(R.id.et1);
        EditText et2 = findViewById(R.id.et2);
        EditText et3 = findViewById(R.id.et3);
        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);

        bt1.setOnClickListener(v -> {
            String name = et1.getText().toString();
            String hobby = et2.getText().toString();
            String city = et3.getText().toString();

            if (name.isEmpty() || hobby.isEmpty() || city.isEmpty())) {
                Toast.makeText(MainActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                if (helper.insertUser(name, hobby, city)) {
                    Toast.makeText(MainActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                    Toast.makeText(MainActivity.this, "INSERT 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "INSERT 실패", Toast.LENGTH_SHORT).show();
                }
                tv.setText(helper.select);
            }
            helper.insertUser(name);
        }
    }
}