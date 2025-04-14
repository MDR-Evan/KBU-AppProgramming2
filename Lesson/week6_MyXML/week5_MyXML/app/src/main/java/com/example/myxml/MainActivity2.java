package com.example.myxml;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity2 extends AppCompatActivity {
    private MyApplication application;
    private ViewPager2 viewPager2;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        application = (MyApplication) getApplication();
        viewPager2 = findViewById(R.id.viewpager2);
        TabLayout tabLayout = findViewById(R.id.tab);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Source");
                        break;
                    case 1:
                        tab.setText("SAX");
                        break;
                    case 2:
                        tab.setText("PULL");
                        break;
                }
            }
        });
        mediator.attach();
        adapter = new FragmentAdapter(MainActivity2.this);
        update();
    }

    private void update() {
        SourceFragment2 sourceFragment2 = new SourceFragment2();
        SAXFragment saxFragment = new SAXFragment();
        PULLFragment pullFragment = new PULLFragment();
        adapter.setFragment(sourceFragment2, saxFragment, pullFragment);
        viewPager2.setAdapter(adapter);
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
        update();
        return true;
    }
}