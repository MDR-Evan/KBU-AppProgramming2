package com.example.week5;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SourceFragment extends Fragment {
    private static Activity activity;
    private TextView textView;
    private MyApplication application;
    private int type;


    public SourceFragment( Activity activity, int type) {
        this.type = type;
        this.activity = activity;
        application = (MyApplication) activity.getApplication();
    }

    public static SourceFragment newInstance(int type) {
        SourceFragment fragment = new SourceFragment(activity, type);
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                application.setXml((String) message.obj);
                textView.setText(application.getXml());
            } else {
                Toast.makeText(activity, "다운로드 실패함", Toast.LENGTH_SHORT).show();
            }
        }
    };

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
            ReadRaw readRaw = new ReadRaw(activity);
            try {
                application.setXml(readRaw.getXML());
            } catch (IOException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            textView.setText(application.getXml());
        }
    }
}
