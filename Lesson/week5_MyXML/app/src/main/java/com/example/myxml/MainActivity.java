package com.example.myxml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
     private TabLayout tabs;
     private SourceFragment sourceFragment = null;
     private ParserFragment parserFragment = null;
     private MyApplication application;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        application = (MyApplication) getApplication();
        FragmentManager manager = getSupportFragmentManager();

        tabs = findViewById(R.id.tabs);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                if (tab.getPosition() == 0) {
                    fragment = sourceFragment;
                } else {
                    fragment = parserFragment;
                }
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        update();


        TabMenu tabMenu = new TabMenu(manager, tabs);
        tabMenu.menu(sourceFragment);
    }

    private void update() {
        sourceFragment = new SourceFragment(MainActivity.this);
        parserFragment = new ParserFragment(MainActivity.this);
        tabs.selectTab(tabs.getTabAt(2));
        tabs.selectTab(tabs.getTabAt(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                application.setType(1);
                break;
            case R.id.item2:
                application.setType(2);
                break;
            case R.id.item3:
                application.setType(3);
                break;
            case R.id.item4:
                application.setType(4);
                break;
            case R.id.item5:
                application.setType(5);
                break;
        }
        item.setChecked(true);
        update();
        return true;
    }
}