package com.example.recyclerviewapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private EmployeeModel[] employeeModels;

    public EmployeeAdapter(EmployeeModel[] employeeModels) {
        this.employeeModels = employeeModels;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View empItem = layoutInflater.inflate(R.layout.activity_employee_item, parent,false);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder(empItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.bind(employeeModels[position]);
    }

    @Override
    public int getItemCount() {
        return employeeModels.length;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView empNoTextView;
        TextView empNameTextView;
        TextView salaryTextView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            empNoTextView = itemView.findViewById(R.id.empNoTextView);
            empNameTextView = itemView.findViewById(R.id.empNameTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
        }

        public void bind(EmployeeModel employeeModel) {
            empNoTextView.setText(String.valueOf(employeeModel.getEmpNo()));
            empNameTextView.setText(employeeModel.getEmpName());
            salaryTextView.setText(String.valueOf(employeeModel.getSalary()));
        }
    }
}
