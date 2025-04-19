package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    private LayoutInflater inflater;

    public WeatherAdapter(@NonNull Context context, int resource, @NonNull List<Weather> objects) {
        super(context, resource, objects);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        Weather item = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap(item.getImage());
        viewHolder.textView1.setText(item.getText1());
        viewHolder.textView2.setText(item.getText2());
        viewHolder.textView3.setText(item.getText3());
        viewHolder.textView4.setText(item.getText4());
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public ViewHolder(View view) {
            this.imageView = view.findViewById(R.id.image);
            this.textView1 = view.findViewById(R.id.textView1);
            this.textView2 =  view.findViewById(R.id.textView2);
            this.textView3 =  view.findViewById(R.id.textView3);
            this.textView4 =  view.findViewById(R.id.textView4);
        }
    }
}
