package com.bignerdranch.android.sudokuapp.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.sudokuapp.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            // This code creates and commits a fragment transaction
            // Fragment transactions are used to add, remove, attach, detach, or replace
            //  fragments in the fragment list
            // Fragment transactions are the heart of how you use fragments to compose
            //  and recompose screens at runtime.
            // The FragmentManager maintains a back stack of fragment transactions that
            //  you can navigate.
            // FragmentManager.beginTransaction creates and returns an instance of
            //  FragmentTransaction, allowing you to chain calls.
            // The add method is the meat of the transaction. Two parameters:
            //  1. Container
            //  2. Fragment

            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
