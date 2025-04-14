package com.example.xmlpaser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private int type = R.id.item1;
    private ViewPager2 viewPager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        adapter = new FragmentAdapter(this);
        update();

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
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

        item.setChecked(true);
        update();
        return true;
    }

    private void update() {
        SourceFragment sourceFragment = new SourceFragment(MainActivity.this, type);
        SAXFragment saxFragment = new SAXFragment();
        PULLFragment pullFragment = new PULLFragment();
        adapter.setFragments(sourceFragment, saxFragment, pullFragment);
        viewPager.setAdapter(adapter);
    }
}