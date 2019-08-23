package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.bignerdranch.android.sudokuapp.R;

public class LanguageActivity extends AppCompatActivity {
    // Tag for debugging
    private static final String TAG = "LanguageActivity";
    // Models
    DBController mDBController;
    // Key for intent extra
    public static final String MODE = "SudokuMode";
    public static final String LISTEN = "ListenMode";
    public static final String PRONUNCIATION = "PronunciationMode";
    public static final String SIZE = "GridSize";
    public static final String LEVEL = "GameLevel";
    // Widgets
    private Button mode1Button;
    private Button mode2Button;
    private int switchFlag;
    private Switch listenSwitch;
    private int pronunciationFlag;
    private Switch pronunciationSwitch;
    private RadioGroup sizeGroup;
    private RadioButton size12RadioButton;
    private RadioGroup levelsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_language);

        mode1Button = (Button) findViewById(R.id.mode1);
        mode2Button = (Button) findViewById(R.id.mode2);

        sizeGroup = (RadioGroup) findViewById(R.id.sizeGroup);
        size12RadioButton = (RadioButton) findViewById(R.id.size12);
        if (!isTablet(this)) {
            size12RadioButton.setVisibility(View.GONE);
        }
        mDBController = new DBController(this);

        levelsGroup = (RadioGroup) findViewById(R.id.levelGroup);


        pronunciationSwitch = (Switch) findViewById(R.id.switch_pronunciation);
        pronunciationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The switch is enabled
                    pronunciationFlag = 1;
                    listenSwitch.setChecked(false);
                } else {
                    // The toggle is disabled
                    pronunciationFlag = 0;
                }
            }
        });

        listenSwitch = (Switch) findViewById(R.id.switch1);
        listenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The switch is enabled
                    switchFlag = 1;
                    pronunciationSwitch.setChecked(false);
                } else {
                    // The toggle is disabled
                    switchFlag = 0;
                }
            }
        });



        mode1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mode 1: Fill in english
                Intent intent = new Intent(LanguageActivity.this, SudokuActivity.class);
                intent.putExtra(MODE, 1);
                if (switchFlag == 1){
                    intent.putExtra(LISTEN,1);
                } else {
                    intent.putExtra(LISTEN,0);
                }
                if (pronunciationFlag == 1){
                    intent.putExtra(PRONUNCIATION,1);
                } else {
                    intent.putExtra(PRONUNCIATION,0);
                }
                intent.putExtra(SIZE, getSize());
                intent.putExtra(LEVEL, getLevel());
                startActivity(intent);

            }
        });
        mode2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mode 2: Fill in 日本語
                Intent intent = new Intent(LanguageActivity.this, SudokuActivity.class);
                intent.putExtra(MODE, 2);
                if (switchFlag == 1){
                    intent.putExtra(LISTEN,1);
                } else {
                    intent.putExtra(LISTEN,0);
                }
                if (pronunciationFlag == 1){
                    intent.putExtra(PRONUNCIATION,1);
                } else {
                    intent.putExtra(PRONUNCIATION,0);
                }

                intent.putExtra(SIZE, getSize());
                intent.putExtra(LEVEL, getLevel());
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

    private int getSize() {
        int selectedID = sizeGroup.getCheckedRadioButtonId();
        switch (selectedID) {
            case R.id.size4:
                return 4;
            case R.id.size6:
                return 6;
            case R.id.size12:
                return 12;
            default:
                return 9;
        }
    }

    private int getLevel() {
        int selectedID = levelsGroup.getCheckedRadioButtonId();
        switch (selectedID) {
            case R.id.mediumLevel:
                return 1;
            case R.id.difficultLevel:
                return 2;
            default: // easy selected by default
                return 0;
        }
    }

    // Code from:
    // https://stackoverflow.com/questions/16784101/how-to-find-tablet-or-phone-in-android-programmatically
    private boolean isTablet(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        boolean xlarge = ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
