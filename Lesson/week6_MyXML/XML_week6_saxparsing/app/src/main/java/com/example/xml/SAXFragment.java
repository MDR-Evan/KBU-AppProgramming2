package com.example.xml;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SAXFragment extends Fragment {
    private Activity activity;
    private MyApplication application;
    private ListView listView;
    private TextView textView;

    public SAXFragment(Activity activity) {
        this.activity = activity;
        this.application = (MyApplication) activity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView;
        if (application.getType() == R.id.item5) {
            rootView = (ViewGroup) inflater.inflate(R.layout.parse_fragment, container, false);
            listView = rootView.findViewById(R.id.listView);
        } else {
            rootView = (ViewGroup) inflater.inflate(R.layout.source_fragment, container, false);
            textView = rootView.findViewById(R.id.textView);
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MySAXParser parser = new MySAXParser(activity);

        if (application.getType() == R.id.item1) {

        } else if (application.getType() == R.id.item2) {

        } else if (application.getType() == R.id.item3) {

        } else if (application.getType() == R.id.item4) {

        } else if (application.getType() == R.id.item5) {
            ArrayList<Country> countries = parser.parsing1(application.getXml());
            ArrayAdapter<Country> adapter = new CountryAdapter(activity, R.layout.country_layout, countries);
            listView.setAdapter(adapter);
        }

    }
}
