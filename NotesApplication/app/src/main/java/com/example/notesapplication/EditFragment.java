package com.example.notesapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditFragment extends Fragment{
    View view;
    EditText titleET, descriptionET;
    Button updateButton;
    int itemId;

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        titleET = view.findViewById(R.id.titleEditText);
        descriptionET = view.findViewById(R.id.descriptionEditText);
        updateButton = view.findViewById(R.id.updateButton);

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemId = bundle.getInt("id");
            String title = bundle.getString("title");
            String description = bundle.getString("description");

            titleET.setText(title);
            descriptionET.setText(description);
        }

        updateButton.setOnClickListener(view -> {
            updateData();
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
//            Log.d("BackStackEntryCount", "Count: " + backStackEntryCount);
//            fragmentManager.popBackStack();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });
        return view;
    }

    private void updateData() {
        String title = titleET.getText().toString();
        String description = descriptionET.getText().toString();

//        if (title.trim().length() == 0) {
//            Toast.makeText(getContext(), "Title is empty", Toast.LENGTH_SHORT).show();
//            return;
//        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyContentProvider.title, title);
        contentValues.put(MyContentProvider.description, description);

        Log.d("Edit Fragment", "content values" + contentValues);

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        int rowsUpdated = getContext().getContentResolver().update(MyContentProvider.CONTENT_URI, contentValues, selection, selectionArgs);

        if(rowsUpdated > 0) {
            Toast.makeText(getContext(), "Data Updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Data Updation failed", Toast.LENGTH_SHORT).show();
        }
    }

}