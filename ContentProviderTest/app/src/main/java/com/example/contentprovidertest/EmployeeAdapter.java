package com.example.contentprovidertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    List<EmployeeData> employeeList;

    Context context;
    ClickListener listener;

    public EmployeeAdapter(List<EmployeeData> employeeList,
                           Context context,
                           ClickListener listener) {
        this.employeeList = employeeList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        View employeeView = inflater.inflate(R.layout.activity_card_layout, parent, false);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder(employeeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {

        holder.bind(employeeList.get(position));
//        final int index = holder.getAdapterPosition();
//        holder.id.setText(employeeList.get(position).id);
//        holder.name.setText(employeeList.get(position).name);
//        holder.dept.setText(employeeList.get(position).dept);
//
//        holder.view.setOnClickListener(view -> listener.click(index));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

