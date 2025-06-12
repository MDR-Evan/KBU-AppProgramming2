package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EmployeeAdapter
        extends RecyclerView.Adapter<EmployeeAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(Employee e);
    }

    private List<Employee> items;
    private Context ctx;
    private OnItemClickListener listener;

    public EmployeeAdapter(Context ctx, List<Employee> items) {
        this.ctx = ctx;
        this.items = items;
    }

    /** 외부에서 items 리스트에 접근하기 위한 getter */
    public List<Employee> getItems() {
        return items;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx)
                .inflate(R.layout.item_employee, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        Employee e = items.get(i);
        h.tvName.setText(e.name + " (" + e.gender + ")");
        h.tvSalary.setText("급여: " + e.salary + "원");
        if (!e.imageUrl.isEmpty()) {
            Glide.with(ctx)
                    .load(e.imageUrl)
                    .circleCrop()
                    .into(h.ivPhoto);
        }
        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(e);
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName, tvSalary;
        VH(View v) {
            super(v);
            ivPhoto  = v.findViewById(R.id.ivPhoto);
            tvName   = v.findViewById(R.id.tvName);
            tvSalary = v.findViewById(R.id.tvSalary);
        }
    }
}
