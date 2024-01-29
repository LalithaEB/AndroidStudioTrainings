package com.elektrobit.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EmployeeAdapter adapter;
    RecyclerView recyclerView;
    ClickListener listener;

    EditText nameET, idET, deptET;
//    Button insert;
    List<EmployeeData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        list = getData();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listener = new ClickListener() {
            @Override
            public void click(int index){
                Toast.makeText(getApplicationContext(), "Clicked item index is "+index, Toast.LENGTH_SHORT).show();
            }
        };
        adapter
                = new EmployeeAdapter(
                list, getApplication(),listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this));
    }

    private List<EmployeeData> getData()
    {
        List<EmployeeData> list = new ArrayList<>();
        list.add(new EmployeeData("Ayush",
                "123456",
                "COS"));
        list.add(new EmployeeData("Vivek",
                "923456",
                "COS"));
        list.add(new EmployeeData("Akshay",
                "823456",
                "COS"));
//        list.add(new EmployeeData("Ayush",
//                "123456",
//                "COS"));
//        list.add(new EmployeeData("Vivek",
//                "923456",
//                "COS"));
//        list.add(new EmployeeData("Akshay",
//                "823456",
//                "COS"));
//        list.add(new EmployeeData("Ayush",
//                "123456",
//                "COS"));
//        list.add(new EmployeeData("Vivek",
//                "923456",
//                "COS"));
//        list.add(new EmployeeData("Akshay",
//                "823456",
//                "COS"));
//        list.add(new EmployeeData("Ayush",
//                "123456",
//                "COS"));
//        list.add(new EmployeeData("Vivek",
//                "923456",
//                "COS"));
//        list.add(new EmployeeData("Akshay",
//                "823456",
//                "COS"));

        return list;
    }

    public void insertData(View view) {
        nameET = findViewById(R.id.nameEditText);
        idET = findViewById(R.id.idEditText);
        deptET = findViewById(R.id.deptEditText);
//        insert = findViewById(R.id.insertButton);

        String name= nameET.getText().toString();
        String id = idET.getText().toString();
        String dept = deptET.getText().toString();

        if(name.isEmpty() || id.isEmpty() || dept.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;

        } else {
            EmployeeData data = new EmployeeData(name, id, dept);
            list.add(data);
            adapter.notifyDataSetChanged();
        }

        nameET.getText().clear();
        idET.getText().clear();
        deptET.getText().clear();
        nameET.requestFocus();
    }
}