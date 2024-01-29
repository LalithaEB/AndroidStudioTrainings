package com.example.employeeinformationapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<EmployeeModel> employeeModels;

    public EmployeeAdapter(List<EmployeeModel> employeeModels) {
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
        holder.bind(employeeModels.get(position));
    }

    @Override
    public int getItemCount() {
        return (employeeModels != null) ? employeeModels.size() : 0;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView empNoTextView;
        TextView empNameTextView;
        TextView designationTextView;
        TextView salaryTextView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            empNoTextView = itemView.findViewById(R.id.empNoTextView);
            empNameTextView = itemView.findViewById(R.id.empNameTextView);
            designationTextView = itemView.findViewById(R.id.designationTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
        }

        public void bind(EmployeeModel employeeModel) {
            empNoTextView.setText("Employee No- " + employeeModel.getEmpNo());
            empNameTextView.setText("Employee Name- " + employeeModel.getEmpName());
            designationTextView.setText("Designation- " + employeeModel.getDesignation());
            salaryTextView.setText("Salary- " + employeeModel.getSalary());
        }
    }
}

