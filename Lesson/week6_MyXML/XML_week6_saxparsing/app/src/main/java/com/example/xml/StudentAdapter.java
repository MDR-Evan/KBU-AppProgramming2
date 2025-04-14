package com.example.xml;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;

    public StudentAdapter(@NonNull Context context, int resource, List<Student> studentList) {
        super(context, resource, studentList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item2_layout, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Student student = getItem(position);
        holder.textView1.setText(student.getName());
        holder.textView2.setText(student.getHakbun());
        holder.textView3.setText(student.getGrade());

        return convertView;
    }


    private class ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;

        public ViewHolder(TextView textView1, TextView textView2, TextView textView3) {
            this.textView1 = textView1;
            this.textView2 = textView2;
            this.textView3 = textView3;
        }

        public ViewHolder(View convertView) {
            textView1 = convertView.findViewById(R.id.textView1);
            textView2 = convertView.findViewById(R.id.textView2);
            textView3 = convertView.findViewById(R.id.textView3);
        }
    }
}
