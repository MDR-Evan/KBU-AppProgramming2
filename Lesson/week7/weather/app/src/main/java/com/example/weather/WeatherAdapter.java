package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        ViewHolder vh;
        Weather item = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.iv.setImageBitmap(item.getBitmap());
        vh.tv1.setText(item.getTv1());
        vh.tv2.setText(item.getTv2());
        vh.tv3.setText(item.getTv3());
        vh.tv4.setText(item.getTv4());

        return convertView;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

        ViewHolder(View view) {
            iv = view.findViewById(R.id.iv);
            tv1 = view.findViewById(R.id.tv1);
            tv2 = view.findViewById(R.id.tv2);
            tv3 = view.findViewById(R.id.tv3);
            tv4 = view.findViewById(R.id.tv4);
        }
    }
}
