package com.example.recyclerviewapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmployeeModel[] employeeModels = new EmployeeModel[] {
                new EmployeeModel(1, "A", 500),
                new EmployeeModel(2, "B", 1000),
                new EmployeeModel(3, "C", 1500),
                new EmployeeModel(1, "A", 500),
                new EmployeeModel(2, "B", 1000),
                new EmployeeModel(3, "C", 1500),
                new EmployeeModel(1, "A", 500),
                new EmployeeModel(2, "B", 1000),
                new EmployeeModel(3, "C", 1500),
                new EmployeeModel(1, "A", 500),
                new EmployeeModel(2, "B", 1000),
                new EmployeeModel(3, "C", 1500)
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if(recyclerView != null) {
            EmployeeAdapter adapter = new EmployeeAdapter(employeeModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}