package com.example.cafeapplication;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setOnLoginClickListener(this);

        fragmentTransaction.replace(R.id.loginFragment, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginClicked() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.mainFrameLayout, new MenuFragment());
        fragmentTransaction.commit();
    }
}