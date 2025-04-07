package com.example.week5;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_fragment, container, false);
        listView = view.findViewById(R.id.listView); // ✅ 반드시 필요
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        MyApplication app = (MyApplication) requireActivity().getApplication();
        String xml = app.getXml();

        if (xml == null || xml.trim().isEmpty()) {
            Toast.makeText(requireContext(), "XML 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        DOMParser parser = new DOMParser(requireContext());
        ArrayList<Person> list = parser.parsingPersonList(xml);
        PersonAdapter adapter = new PersonAdapter(requireContext(), list);
        listView.setAdapter(adapter); // ← listView가 null이면 여기서 터짐
    }
}
