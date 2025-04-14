package com.example.xml;

import android.app.Activity;
import android.app.Application;
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

public class ParseFragment extends Fragment {
    private MyApplication application;
    private Activity activity;
    private TextView textView;
    private ListView listView;


    public ParseFragment(Activity activity) {
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (application.getType() == 3 || application.getType() == 5 ) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.parse_fragment, container, false);
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
        if (application.getType() == 3 || application.getType() == 5) {
            if (application.getType() == 3) {
                ArrayList<Student> result = parser.parsing2(application.getXml());
                StudentAdapter adapter = new StudentAdapter(activity, R.layout.item2_layout, result);
                listView.setAdapter(adapter);
            } else if (application.getType() == 5) {
                ArrayList<Country> result = parser.parsing4(application.getXml());
                CountryAdapter adapter = new CountryAdapter(activity, R.layout.country_layout, result);
                listView.setAdapter(adapter);
            }
        } else {
            if (application.getType() == 1)
                textView.setText(parser.parsing(application.getXml()));
            else if (application.getType() == 2)
                textView.setText(parser.parsing1(application.getXml()));
            else if (application.getType() == 4)
                textView.setText(parser.parsing3(application.getXml()));
        }

    }
}
