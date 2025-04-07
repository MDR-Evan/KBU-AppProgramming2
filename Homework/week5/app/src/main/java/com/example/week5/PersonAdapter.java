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
    public Object getItem(int i) {
        return people.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textId = convertView.findViewById(R.id.text_id);
        TextView textName = convertView.findViewById(R.id.text_name);
        TextView textAge = convertView.findViewById(R.id.text_age);

        Person person = people.get(position);

        textId.setText(person.getId());
        textName.setText(person.getName());
        textAge.setText(person.getAge() + " 세");

        return convertView;
    }
}

