package com.example.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EmployeeAdapter adapter;
    RecyclerView recyclerView;
    ClickListener listener;
    EditText idET, nameET, deptET;
    Button insertData;
    List<EmployeeData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertData = findViewById(R.id.insertButton);
        insertData.setOnClickListener(view -> {
            onClickAddDetails(view);
        });

        recyclerView = findViewById(
                R.id.recyclerView);
        listener = index -> Toast.makeText(getApplicationContext(), "Clicked item index is "+index, Toast.LENGTH_SHORT).show();

        adapter = new EmployeeAdapter(list, getApplication(),listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        try {

            idET = findViewById(R.id.idEditText);
            nameET = findViewById(R.id.nameEditText);
            deptET = findViewById(R.id.deptEditText);

            String idString = idET.getText().toString();
            String nameString = nameET.getText().toString();
            String deptString = deptET.getText().toString();

            // class to add values in the database
            ContentValues values = new ContentValues();

            // fetching text from user
            values.put(MyContentProvider.id, idString);
            values.put(MyContentProvider.name, nameString);
            values.put(MyContentProvider.dept, deptString);

            Log.d("LALITHA", "id name dept" + idString + nameString + deptString);

            // inserting into database through content URI
            getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
            Log.d("LALITHA", "values" + values);

            // displaying a toast message
            Toast.makeText(this, "New Record Inserted", Toast.LENGTH_LONG).show();

            idET.setText("");
            nameET.setText("");
            deptET.setText("");

            onClickShowDetails(view);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error adding details", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        // inserting complete table details in this text field
//        TextView resultView= (TextView) findViewById(R.id.res);

        // creating a cursor object of the
        // content URI
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,
                null, null, null, null);
        try {


            // iteration of the cursor
            // to print whole table
            if (cursor != null && cursor.moveToFirst()) {
                list.clear();
                do {
                    int idIndex = cursor.getColumnIndex(MyContentProvider.id);
                    int nameIndex = cursor.getColumnIndex(MyContentProvider.name);
                    int deptIndex = cursor.getColumnIndex(MyContentProvider.dept);

                    if (idIndex != -1 && nameIndex != -1 && deptIndex != -1) {
                        String id = cursor.getString(idIndex);
                        String name = cursor.getString(nameIndex);
                        String dept = cursor.getString(deptIndex);

                        list.add(new EmployeeData(id, name, dept));
                    }
                } while (cursor.moveToNext());

            } else {
                list.clear();
            }

            if (recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error showing details", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}