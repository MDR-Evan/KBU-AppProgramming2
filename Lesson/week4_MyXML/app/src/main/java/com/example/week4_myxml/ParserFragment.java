package com.example.week4_myxml;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ParserFragment extends Fragment {
    private Activity activity;
    private MyApplication application;
    private TextView textView;

    public ParserFragment(Activity activity) {
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.source_fragment, container, false);
        textView = viewGroup.findViewById(R.id.textView);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DOMParser parser = new DOMParser(activity);
        if (application.getType() == 1) {
            textView.setText(parser.parsing1(application.getXml()));
        } else {
            textView.setText(parser.parsing2(application.getXml()));
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
