package com.example.myxml;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class SourceFragment extends Fragment {
    private Activity activity;
    private TextView textView;
    private MyApplication application;


    public SourceFragment( Activity activity) {

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
        if (application.getType() == 1) {
            DOMMaker maker = new DOMMaker(activity);
            application.setXml(maker.makeSource());
            textView.setText(application.getXml());
        } else if (application.getType() == 2) {
            ReadRaw readRaw = new ReadRaw(activity);
            try {
                application.setXml(readRaw.getXML());
            } catch (IOException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            textView.setText(application.getXml());
        } else if (application.getType() == 3) {
            ReadAssets readAssets = new ReadAssets(activity);
            try {
                application.setXml(readAssets.getXML());
            } catch (IOException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            textView.setText(application.getXml());
        }
    }
}
