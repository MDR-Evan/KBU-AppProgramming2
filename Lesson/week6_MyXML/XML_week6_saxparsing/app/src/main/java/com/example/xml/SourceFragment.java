package com.example.xml;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;

public class SourceFragment extends Fragment {
    private MyApplication application;
    private Activity activity;
    private TextView textView;

    public SourceFragment(Activity activity) {
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
            DOMMaker domMaker = new DOMMaker(activity);
            application.setXml(domMaker.source());
            textView.setText(application.getXml());
        } else if (application.getType() == 2) {
            ReadRaw readRaw = new ReadRaw(activity);
            application.setXml(readRaw.getXML());
            textView.setText(application.getXml());
        }  else if (application.getType() == 3) {
            ReadAsset readAsset = new ReadAsset(activity);
            application.setXml(readAsset.getXML());
            textView.setText(application.getXml());
        }else if (application.getType() == 4) {
            XMLThread xmlThread = new XMLThread(activity, application.getPage(0));
            xmlThread.start();
            try {
                xmlThread.join();
                application.setXml(xmlThread.getResult());
                textView.setText(application.getXml());
            } catch (InterruptedException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if (application.getType() == 5) {
            XMLAsyncTask asyncTask =  new XMLAsyncTask(activity);
            try {
                String result = asyncTask.execute(application.getPage(1)).get();
                application.setXml(result);
                textView.setText(application.getXml());
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }


    }
}
