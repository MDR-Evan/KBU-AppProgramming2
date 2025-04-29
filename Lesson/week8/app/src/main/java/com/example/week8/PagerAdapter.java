package com.example.week8;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SourceFragment();
            case 1: return new DomFragment();
            case 2: return new SaxFragment();
            default: return new SourceFragment();
        }
    }

    @Override
    public int getCount() {
        return 3; // 탭 개수
    }
}
