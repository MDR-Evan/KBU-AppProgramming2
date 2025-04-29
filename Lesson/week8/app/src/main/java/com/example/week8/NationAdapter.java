package com.example.week8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NationAdapter extends BaseAdapter {
    private final List<Nation> list;
    private final LayoutInflater inflater;

    public NationAdapter(Context ctx, List<Nation> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount() { return list.size(); }
    @Override public Object getItem(int i) { return list.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dom_nation, parent, false);
            vh = new ViewHolder();
            vh.ivFlag    = convertView.findViewById(R.id.ivFlagItem);
            vh.tvCountry = convertView.findViewById(R.id.tvCountryItem);
            vh.tvTemp    = convertView.findViewById(R.id.tvTempItem);
            vh.tvWeather = convertView.findViewById(R.id.tvWeatherItem);
            vh.ivIcon    = convertView.findViewById(R.id.ivIconItem);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Nation n = list.get(pos);
        vh.tvCountry.setText(n.country);
        vh.tvTemp   .setText(n.temperature + "℃");
        vh.tvWeather.setText(n.weather);
        Glide.with(vh.ivFlag.getContext()).load(n.flagUrl).into(vh.ivFlag);
        vh.ivIcon.setImageResource(getWeatherIconRes(n.weather));

        return convertView;
    }

    int getWeatherIconRes(String weather) {
        switch (weather) {
            case "맑음": return R.drawable.nb01;
            case "흐림": return R.drawable.nb04;
            case "비":   return R.drawable.nb08;
            case "눈":   return R.drawable.nb11;
            case "우박": return R.drawable.nb07;
            default:     return R.drawable.nb01;
        }
    }

    private static class ViewHolder {
        ImageView ivFlag, ivIcon;
        TextView tvCountry, tvTemp, tvWeather;
    }
}
