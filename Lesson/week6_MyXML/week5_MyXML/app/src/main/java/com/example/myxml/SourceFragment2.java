package com.example.myxml;

import android.app.Activity;
import android.graphics.Color;
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

public class SourceFragment2 extends Fragment {
    private Activity activity;
    private MyApplication application;
    private TextView textView;

    public SourceFragment2(Activity activity) {
        this.activity = activity.getApplication()
        ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.source_fragment, container, false);
        textView = rootView.findViewById(R.id.textView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (application.getType() == R.id.item1) {

        } else if (application.getType() == R.id.item2) {

        } else if (application.getType() == R.id.item3) {

        } else if (application.getType() == R.id.item4) {

        } else if (application.getType() == R.id.item5) {
            DownloadAsyncTask asyncTask = new DownloadAsyncTask(activity);
            try {
                application.setXml(asyncTask.execute(application.getPage(1)).get();
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            })
            textView.setText(application.getXml());
            textView.setTextColor(Color.BLUE);
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
