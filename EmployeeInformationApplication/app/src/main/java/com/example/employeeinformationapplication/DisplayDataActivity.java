package com.example.employeeinformationapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayDataActivity extends AppCompatActivity {
    private List<EmployeeModel> employeeModels;
    RecyclerView recyclerView;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        recyclerView = findViewById(R.id.recyclerView);
        employeeModels = new ArrayList<>();

        adapter = new EmployeeAdapter(employeeModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        onClickLoadData();

    }

    public void onClickLoadData() {

        try (Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {

                try {
                    // Clear the existing data in the list
                    employeeModels.clear();

                    // Loop through the cursor data
                    do {
                        int empNoIndex = cursor.getColumnIndex(MyContentProvider.empNo);
                        int empNameIndex = cursor.getColumnIndex(MyContentProvider.empName);
                        int designationIndex = cursor.getColumnIndex(MyContentProvider.designation);
                        int salaryIndex = cursor.getColumnIndex(MyContentProvider.salary);

                        // Check if column indices are valid (-1 means the column does not exist)
                        if (empNoIndex != -1 && empNameIndex != -1 && designationIndex != -1 && salaryIndex != -1) {
                            int empNo = Integer.parseInt(cursor.getString(empNoIndex));
                            String empName = cursor.getString(empNameIndex);
                            String designation = cursor.getString(designationIndex);
                            int salary = Integer.parseInt(cursor.getString(salaryIndex));

                            Log.d("DisplayDataActivity", "empNo: " + empNo + ", empName: " + empName + ", designation: " + designation + ", salary: " + salary);

                            // Add a new EmployeeModel to the list
                            employeeModels.add(new EmployeeModel(empNo, empName, designation, salary));
                        } else {
                            Log.w("DisplayDataActivity", "One or more column indices are not valid");
                        }
                    } while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
            } else {
                // No data in the cursor, set the list to be empty
                employeeModels.clear();
            }
        }
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}
