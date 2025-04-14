package com.example.xml;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class TabMenu {
    private FragmentManager manager;
    private TabLayout tabs;

    public TabMenu(FragmentManager manager, TabLayout tabs) {
        this.manager = manager;
        this.tabs = tabs;
    }

    public void menu(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
        tabs.addTab(tabs.newTab().setText("Source"));
        tabs.addTab(tabs.newTab().setText("DOM Parsing"));
    }
}
