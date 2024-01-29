package com.example.contentprovidertest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewHolder extends RecyclerView.ViewHolder{

    TextView idTextView, nameTextView, deptTextView;
    View view;
    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
        idTextView = itemView.findViewById(R.id.employee_id);
        nameTextView = itemView.findViewById(R.id.employee_name);
        deptTextView = itemView.findViewById(R.id.employee_dept);
        view = itemView;
    }

    public void bind(EmployeeData employeeData) {
        idTextView.setText("Id- " + employeeData.getId());
        nameTextView.setText("Name- " + employeeData.getName());
        deptTextView.setText("Dept- " + employeeData.getDept());
    }
}
