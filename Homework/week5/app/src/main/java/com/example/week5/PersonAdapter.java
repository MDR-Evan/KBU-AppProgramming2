package com.example.week5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Person> people;

    public PersonAdapter(Context context, ArrayList<Person> people) {
        this.context = context;
        this.people = people;
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        TextView textId = convertView.findViewById(R.id.text_id);
        TextView textName = convertView.findViewById(R.id.text_name);
        TextView textAge = convertView.findViewById(R.id.text_age);

        Person person = people.get(position);

        textId.setText("ID: " + person.getId());
        textName.setText("이름: " + person.getName());
        textAge.setText(person.getAge() + " 세");

        return convertView;
    }
}