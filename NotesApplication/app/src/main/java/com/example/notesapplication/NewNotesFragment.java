package com.example.notesapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNotesFragment extends Fragment {
    View view;
    EditText titleET, descriptionET;
    Button saveButton;

    public NewNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_notes, container, false);

        titleET = view.findViewById(R.id.newTitleEditText);
        descriptionET = view.findViewById(R.id.newDescriptionEditText);
        saveButton = view.findViewById(R.id.newSaveButton);
        saveButton.setOnClickListener(view -> {
            insertData();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });
        return view;
    }

    private void insertData() {
        String title = titleET.getText().toString();
        String description = descriptionET.getText().toString();

        if (title.trim().length() == 0) {
            Toast.makeText(getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(MyContentProvider.title, title);
        contentValues.put(MyContentProvider.description, description);

        requireContext().getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);

        Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
    }
}