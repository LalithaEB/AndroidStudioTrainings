package com.example.cafeapplication;

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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    View view;
    TextView loginTextView;
    EditText userNameET;
    EditText passwordET;
    Button loginButton;
    SignUpFragment signUpFragment;
    MenuFragment menuFragment;

    private OnLoginClickListener mListener;

    public LoginFragment() {
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
        view = inflater.inflate(R.layout.fragment_login, container, false);

        loginTextView = view.findViewById(R.id.loginTextView);
        userNameET = view.findViewById(R.id.loginUserName);
        passwordET = view.findViewById(R.id.loginPassword);

        signUpFragment = new SignUpFragment();
        menuFragment = new MenuFragment();

        loginTextView.setOnClickListener(view -> {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.replace(R.id.loginFrameLayout, signUpFragment);
            fragmentTransaction.commit();
        });

        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            boolean isCheck = onClickCheckDetails();

            Log.d("Fragment", "Current Fragment: " + getClass().getSimpleName());

            if (isCheck && mListener != null) {
                mListener.onLoginClicked();
                Log.d("LALITHA", "check passed -" + isCheck);
            } else {
                Log.d("LALITHA", "check failed -" + isCheck);
            }
        });

        return view;
    }

    public boolean onClickCheckDetails() {

        boolean check = false;

        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            userNameET.setError("Cannot be empty");
        } else if (TextUtils.isEmpty(password)) {
            passwordET.setError("Cannot be empty");
        }

        Cursor cursor = getActivity().getContentResolver().query(Uri.parse(MyContentProvider.CONTENT_URI.toString()), null, null, null, null);

        if (cursor.moveToFirst()) {
            int userNameIndex = cursor.getColumnIndex("userName");
            int passwordIndex = cursor.getColumnIndex("password");

            do {
                String name = cursor.getString(userNameIndex);
                String pass = cursor.getString(passwordIndex);

                if (name.equals(userName) && pass.equals(password)) {
                    check = true;
                    Toast.makeText(getContext(), "Username exists\nPassword matches", Toast.LENGTH_SHORT).show();

                    Log.d("LALITHA", "username exists- " + name);
                    Log.d("LALITHA", "password matches- " + pass);
                }
            } while (cursor.moveToNext());

            if(check == false) {
                Toast.makeText(getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                Log.d("LALITHA", "wrong credentials- " + userName + " " + password);
            }

        } else {
            Toast.makeText(getContext(), "No user found", Toast.LENGTH_SHORT).show();
            Log.d("LALITHA", "No user found");
        }
        cursor.close();
        return check;
    }

    public void setOnLoginClickListener(OnLoginClickListener listener) {
        this.mListener = listener;
    }

    public interface OnLoginClickListener {
        void onLoginClicked();
    }
}