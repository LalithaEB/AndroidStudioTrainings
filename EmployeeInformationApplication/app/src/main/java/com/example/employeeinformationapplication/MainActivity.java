package com.example.employeeinformationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        EditText empNo = findViewById(R.id.empNoEditText);
        EditText empName = findViewById(R.id.empNameEditText);
        EditText designation = findViewById(R.id.empDesignationEditText);
        EditText salary = findViewById(R.id.empSalaryEditText);

        ContentValues values = new ContentValues();
        try {
            values.put(MyContentProvider.empNo, Integer.parseInt(empNo.getText().toString()));
            values.put(MyContentProvider.empName, empName.getText().toString());
            values.put(MyContentProvider.designation, designation.getText().toString());
            values.put(MyContentProvider.salary, Integer.parseInt(salary.getText().toString()));

            getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
            Toast.makeText(this, "New Record Inserted", Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error inserting record", Toast.LENGTH_LONG).show();
        }

        empNo.setText("");
        empName.setText("");
        designation.setText("");
        salary.setText("");
    }

    public void onClickLoadDetails(View view){
        Intent intent = new Intent(getApplicationContext(), DisplayDataActivity.class);
        startActivity(intent);
    }
}