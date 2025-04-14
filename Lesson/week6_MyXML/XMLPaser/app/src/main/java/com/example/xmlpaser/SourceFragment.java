package com.example.xmlpaser;

import android.app.Activity;
import android.app.Application;
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

public class SourceFragment extends Fragment {
    private MyApplication application;
    private Activity activity;
    private int type ;
    private TextView textView;



    public SourceFragment(Activity activity, int type) {
        application = (MyApplication) activity.getApplication();
        this.activity = activity;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.source_fragment, container, false);
        textView = rootView.findViewById(R.id.textView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (type == R.id.item1) {

        } else if (type == R.id.item2) {

        } else if (type == R.id.item3) {

        } else if (type == R.id.item4) {


        } else if (type == R.id.item5) {
            DownAsyncTask task = new DownAsyncTask(activity);
            try {
                application.setXml(task.execute(application.getPage(1)).get());

            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        textView.setText(application.getXml());
        textView.setTextColor(Color.BLACK);
    }
}
