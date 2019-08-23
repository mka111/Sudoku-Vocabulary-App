package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.sudokuapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button playButton;
    private Button addWordsButton;
    private Button flashcardsButton;
    private DBController mDBController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mDBController = new DBController(this);
        mDBController.onCreate();

        playButton = (Button) findViewById(R.id.play);
        addWordsButton = (Button) findViewById(R.id.add_word);
        flashcardsButton = (Button) findViewById(R.id.flashcards);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to language activity
                Intent intent = new Intent(MainActivity.this, ModeActivity.class);
                startActivity(intent);
            }
        });
        addWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to language activity
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });
        flashcardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to WordBankActivity
                Intent intent = new Intent(MainActivity.this, WordBankActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
