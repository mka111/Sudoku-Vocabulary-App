package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.sudokuapp.R;

public class ModeActivity extends AppCompatActivity {

    private Button normalModeButton;
    private Button difficultModeButton;
    private Button teacherModeButton;

    private DBController mDBController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        normalModeButton = (Button) findViewById(R.id.mode_normal);
        difficultModeButton = (Button) findViewById(R.id.mode_difficult);
        teacherModeButton = (Button) findViewById(R.id.mode_teacher);

        mDBController = new DBController(this);
        if (mDBController.noTeacherWords()){
            teacherModeButton.setVisibility(View.GONE);
        } else {
            teacherModeButton.setVisibility(View.VISIBLE);
        }

        normalModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play with 9 random words
                Intent intent = new Intent(ModeActivity.this, LanguageActivity.class);
                SharedPreferences pref = getSharedPreferences("GamePref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("GameMode", 0);
                editor.apply();

                startActivity(intent);
            }
        });
        difficultModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play with 9 random words from the top 100 most difficult ones
                Intent intent = new Intent(ModeActivity.this, LanguageActivity.class);
                SharedPreferences pref = getSharedPreferences("GamePref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("GameMode", 1);
                editor.apply();

                startActivity(intent);
            }
        });
        teacherModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play with 9 random words from teacher
                Intent intent = new Intent(ModeActivity.this, LanguageActivity.class);
                SharedPreferences pref = getSharedPreferences("GamePref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("GameMode", 2);
                editor.apply();

                startActivity(intent);
            }
        });
    }
}
