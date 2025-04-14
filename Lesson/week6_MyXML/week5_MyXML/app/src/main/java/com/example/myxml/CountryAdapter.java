package com.example.myxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {
    private Context context;

    public CountryAdapter(@NonNull Context context, int resource, @NonNull List<Country> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.country_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Country country = getItem(position);
        holder.textView1.setText("국가 : " + country.getName());
        holder.textView2.setText("수도 : " + country.getCapital() + "언어" + country.getLang());
        holder.textView3.setText("화폐" + country.getCurrencyName() + "(" + country.getCode() + ")");

        Glide.with(context).load(country.getFlag()).into(holder.imageView);
        return convertView;
    }

    private class ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private ImageView imageView;

        public ViewHolder(View view) {
            this.imageView = view.findViewById(R.id.imageView);
            this.textView1 = view.findViewById(R.id.textView1);
            this.textView2 = view.findViewById(R.id.textView2);
            this.textView3 = view.findViewById(R.id.textView3);
        }
    }
}
