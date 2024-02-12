package com.example.cafeapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpFragment extends Fragment {
    View view;
    EditText userNameET;
    EditText passwordET;
    TextView signUpTextView;
    Button signUpButton;
    LoginFragment loginFragment;

    public SignUpFragment() {
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
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        userNameET = view.findViewById(R.id.signUpUsername);
        passwordET = view.findViewById(R.id.signUpPassword);
        signUpTextView = view.findViewById(R.id.signUpTextView);
        loginFragment = new LoginFragment();

        signUpTextView.setOnClickListener(view -> {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.replace(R.id.signUpFrameLayout, loginFragment);
            fragmentTransaction.commit();
        });

        signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> {
            onClickInsertDetails();
        });
        return view;
    }

    private void onClickInsertDetails() {

        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();

//        if(userName == null || password == null) {
//            Log.e("Signup fragment", "Edit Text views are null");
//            return;
//        }
        if (userName.trim().length() == 0) {
            userNameET.setError("Cannot be empty");
            return;
        } else if (password.trim().length() == 0) {
            passwordET.setError("Cannot be empty");
            return;
        }

        Cursor cursor = getActivity().getContentResolver().query(Uri.parse(MyContentProvider.CONTENT_URI.toString()), null, null, null, null);

        if (cursor.moveToFirst()) {
            int userNameIndex = cursor.getColumnIndex("userName");
//            int passwordIndex = cursor.getColumnIndex("password");

            do {
                String name = cursor.getString(userNameIndex);
//            String pass = cursor.getString(passwordIndex);

                if (userName.equals(name)) {
                    Toast.makeText(getContext(), "User name exists", Toast.LENGTH_SHORT).show();
                    Log.d("LALITHA", "Username- " + name);
                }
            } while (cursor.moveToNext());

        } else {
            Toast.makeText(getContext(), "No user found", Toast.LENGTH_SHORT).show();
            Log.d("LALITHA", "No user found");
        }

        cursor.close();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MyContentProvider.userName, userName);
        contentValues.put(MyContentProvider.password, password);

        Log.d("LALITHA", "username- " + userName + ", " + "password- " + password);

        requireContext().getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
        Log.d("LALITHA", "content values- " + contentValues);

        Toast.makeText(getContext(), "New record inserted", Toast.LENGTH_SHORT).show();

        userNameET.setText("");
        passwordET.setText("");
    }
}