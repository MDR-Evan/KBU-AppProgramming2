package com.example.myxml;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ParserFragment extends Fragment {
    private Activity activity;
    private MyApplication application;
    private TextView textView;
    private ListView listView;

    public ParserFragment(Activity activity) {
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (application.getType() == 3 || application.getType() == 5) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.listview_fragment, container, false);
            listView = viewGroup.findViewById(R.id.listView);
            return viewGroup;
        } else {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.source_fragment, container, false);
            textView = viewGroup.findViewById(R.id.textView);
            return viewGroup;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DOMParser parser = new DOMParser(activity);
        if (application.getType() == 3) {
            ArrayList<Student> students = parser.parsing3(application.getXml());
            StudentAdapter adapter = new StudentAdapter(activity, R.layout.student_layout, students);
            listView.setAdapter(adapter);
        } else if (application.getType() == 5) {
            ArrayList<Country> countries = parser.parsing5(application.getXml());
            CountryAdapter adapter = new CountryAdapter(activity, R.layout.country_layout, countries);
            listView.setAdapter(adapter);
        } else {
            if (application.getType() == 1) {
                textView.setText(parser.parsing1(application.getXml()));
            } else if (application.getType() == 2) {
                textView.setText(parser.parsing2(application.getXml()));
            } else if (application.getType() == 4) {
                textView.setText(parser.parsing4(application.getXml()));
            }
        }
    }
}
