package com.example.afinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.afinal.R;
import com.example.afinal.Sawon;

import java.util.List;

public class SawonAdapter extends BaseAdapter {
    private final Context context;
    private final List<Sawon> data;
    private final boolean useCircle;
    private final LayoutInflater inflater;

    public SawonAdapter(Context c, List<Sawon> data, boolean useCircle) {
        this.context   = c;
        this.data      = data;
        this.useCircle = useCircle;
        this.inflater  = LayoutInflater.from(c);
    }

    @Override public int getCount()            { return data.size(); }
    @Override public Sawon getItem(int pos)   { return data.get(pos); }
    @Override public long getItemId(int pos)  { return getItem(pos).getId(); }

    @Override
    public View getView(int pos, View cv, ViewGroup parent) {
        ViewHolder vh;
        if (cv == null) {
            cv = inflater.inflate(R.layout.item_sawon, parent, false);
            vh = new ViewHolder();
            vh.img = cv.findViewById(R.id.imgProfile);
            vh.tvN = cv.findViewById(R.id.tvName);
            vh.tvS = cv.findViewById(R.id.tvSalary);
            cv.setTag(vh);
        } else vh = (ViewHolder) cv.getTag();

        Sawon s = getItem(pos);
        vh.tvN.setText(s.getName());
        vh.tvS.setText(String.format("%,d원", s.getSalary()));

        RequestOptions opts = useCircle
                ? RequestOptions.circleCropTransform()
                : new RequestOptions().centerCrop();

        Glide.with(context)
                .load(s.getImageUrl())
                .apply(opts)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(vh.img);

        return cv;
    }

    static class ViewHolder {
        ImageView img;
        TextView  tvN, tvS;
    }
}
