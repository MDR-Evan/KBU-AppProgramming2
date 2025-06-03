package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private static final String[] TAB_TITLES = { "Source", "DOM", "SAX", "PULL" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout TL = findViewById(R.id.TL);
        ViewPager2 VP2 = findViewById(R.id.VP2);

        // ViewPager2 어댑터 설정
        VP2.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return TAB_TITLES.length;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new SourceFragment();
                    case 1:
                        return new DOMFragment();
                    case 2:
                        return new SAXFragment();
                    case 3:
                        return new PullFragment();
                    default:
                        throw new IllegalStateException("Unexpected position " + position);
                }
            }
        });

        // TabLayout 과 ViewPager2 연동
        new TabLayoutMediator(TL, VP2,
                (tab, position) -> tab.setText(TAB_TITLES[position])
        ).attach();
    }
}
