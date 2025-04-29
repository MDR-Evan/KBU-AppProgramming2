package com.example.week8;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private int type = 1;
    private MyApplication application;
    private boolean isMusicOn = true;

    private RadioGroup RG;
    private RadioButton RB1, RB2, RB3, RB4;
    private TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Weather");
        }

        RG  = findViewById(R.id.RG);
        RB1 = findViewById(R.id.RB1);
        RB2 = findViewById(R.id.RB2);
        RB3 = findViewById(R.id.RB3);
        RB4 = findViewById(R.id.RB4);

        FrameLayout container = findViewById(R.id.container);

        // 텍스트 표시용 ScrollView 생성
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        TV = new TextView(this);
        TV.setId(View.generateViewId());
        TV.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        ));

        scrollView.addView(TV);

        // 기본 화면 설정
        container.removeAllViews();
        container.addView(scrollView);

        // 앱 시작 시 음악 서비스 실행
        ContextCompat.startForegroundService(
                this,
                new Intent(this, MusicStartedService.class)
        );

        RG.setOnCheckedChangeListener((group, checkedId) -> {
            if (RB1.isChecked()) {
                TextThread thread = new TextThread(
                        application.getPage(0),
                        MainActivity.this,
                        new Handler(),
                        result -> {
                            TV.setTextColor(Color.BLUE);
                            TV.setText(result);
                        }
                );
                thread.start();
                container.removeAllViews();
                container.addView(scrollView); // 다시 텍스트뷰 붙이기
            } else if (RB2.isChecked()) {
                replaceFragment(new DomFragment());
            } else if (RB3.isChecked()) {
                replaceFragment(new SaxFragment());
            } else if (RB4.isChecked()) {
                replaceFragment(new PullFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem musicItem = menu.findItem(R.id.item1);
        musicItem.setChecked(isMusicOn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            isMusicOn = !item.isChecked();
            item.setChecked(isMusicOn);

            Intent intent = new Intent(MainActivity.this, MusicStartedService.class);

            if (isMusicOn) {
                ContextCompat.startForegroundService(this, intent);
            } else {
                stopService(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicStartedService.class));
    }
}
