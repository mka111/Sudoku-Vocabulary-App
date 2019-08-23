package com.bignerdranch.android.sudokuapp.Controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bignerdranch.android.sudokuapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AddWordActivity extends AppCompatActivity {

    DBController mDBController;

    private static final String TAG = "AddWordActivity";

    private Button teacherButton;
    private Button otherButton;
    
    private static final int READ_REQUEST_CODE_TEACHER = 20;
    private static final int READ_REQUEST_CODE_OTHER = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_add_word);

        teacherButton = (Button) findViewById(R.id.add_word_teacher);
        otherButton = (Button) findViewById(R.id.add_word_other);
        mDBController = new DBController(this);

        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent, READ_REQUEST_CODE_TEACHER);
            }
        });
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent, READ_REQUEST_CODE_OTHER);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == READ_REQUEST_CODE_OTHER || requestCode == READ_REQUEST_CODE_TEACHER)
                && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (data != null) {
                uri = data.getData();
                String isTeacherWord = "0";
                if (requestCode == READ_REQUEST_CODE_TEACHER) {
                    isTeacherWord = "1";
                }
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream));
                String line = "";
                try {
                    boolean insertData = false;
                    while ((line = reader.readLine()) != null) {
                        String[] token = line.split(",");
                        insertData = mDBController.InsertData(token[0], token[1], isTeacherWord);
                        Log.wtf("String", token[0]);
                    }
                    if (insertData) {
                        Message("Words Added Successfully!");
                    } else {
                        Message("Not Successful");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void Message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
