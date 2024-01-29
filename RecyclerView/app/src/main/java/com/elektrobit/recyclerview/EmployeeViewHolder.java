package com.elektrobit.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewHolder extends RecyclerView.ViewHolder{

    TextView name, id, dept;
    View view;
    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employee_name);
        id = itemView.findViewById(R.id.employee_id);
        dept = itemView.findViewById(R.id.employee_dept);
        view = itemView;
    }
}
