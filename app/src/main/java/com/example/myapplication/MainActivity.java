package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(getSupportFragmentManager(), new FragmentSpinner());
    }

    public static void loadFragment(FragmentManager fm, Fragment fragment) {
        //method to navigate in between fragments
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // addToBackStack handles the onBackPressed in fragments
        fragmentTransaction.replace(R.id.content_container, fragment, "").addToBackStack("tag");
        fragmentTransaction.commit();
    }
}