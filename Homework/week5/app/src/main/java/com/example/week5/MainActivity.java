package com.example.week5;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager2 viewPager;
    private MyApplication application;
    private int type = R.id.item1;
    private SourceFragment sourceFragment = null;
    private ParserFragment parserFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentAdapter(this));

        new TabLayoutMediator(tabs, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Source");
            } else {
                tab.setText("DOM Parsing");
            }
        }).attach();
    }

    private class FragmentAdapter extends FragmentStateAdapter {
        public FragmentAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            application = (MyApplication) getApplicationContext();
            if (position == 0) {
                return new SourceFragment(MainActivity.this, type);  // or your logic for type
            } else {
                return new ParserFragment(MainActivity.this);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        type = item.getItemId();
        if (type == 1) {
            application.setType(1);
        } else if (type == 2) {
            application.setType(2);
        } else if (type == 3) {
            application.setType(3);
        } else if (type == 4) {
            application.setType(4);
        } else if (type == 5) {
            application.setType(5);
        }

        item.setChecked(true);
        return true;
    }

    private void update() {
        sourceFragment = new SourceFragment(MainActivity.this, type);
        parserFragment = new ParserFragment(MainActivity.this);
        tabs.selectTab(tabs.getTabAt(2));
        tabs.selectTab(tabs.getTabAt(0));
    }
}