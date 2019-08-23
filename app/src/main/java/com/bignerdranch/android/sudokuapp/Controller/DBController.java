package com.bignerdranch.android.sudokuapp.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bignerdranch.android.sudokuapp.Model.WordPair;

import java.util.ArrayList;
import java.util.List;

public class DBController extends SQLiteOpenHelper {
    private static final String TAG = "DBController";
    private static final int NUMBER_OF_TOP_DIFFICULT_WORDS = 100;

    // Parameters of words' table
    private static final String TABLE_NAME = "data_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "EngWord";
    public static final String COL3 = "JapWord";
    private static final String COL4 = "IsTeacher";
    public static final String COL5 = "Difficulty";

    public DBController(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " +  TABLE_NAME +"(ID INTEGER PRIMARY KEY," + COL2 +
                " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " INTEGER)";
        db.execSQL(query);
        if (checkForTableEmpty(db)) {
            InsertData(db, "Hello", "こんにちは", "0");
            InsertData(db,"Goodbye", "さようなら", "0");
            InsertData(db,"Thank you", "ありがとう", "0");
            InsertData(db,"Sorry", "ごめんなさい", "0");
            InsertData(db,"Excuse me", "すみません", "0");
            InsertData(db,"Please", "お願いします", "0");
            InsertData(db,"Of course", "もちろん", "0");
            InsertData(db,"No", "いいえ", "0");
            InsertData(db,"Yes", "はい", "0");
            InsertData(db,"Good night", "おやすみ", "0");
            InsertData(db,"Good evening", "こんばんは", "0");
            InsertData(db,"Good morning", "おはよう", "0");
        }
    }

    public void onCreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public boolean InsertData(String item1, String item2, String item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        return InsertData(db, item1, item2, item3);
    }

    private boolean InsertData(SQLiteDatabase db, String item1, String item2, String item3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, 0);

        Log.d(TAG, "Inserting data to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    // UPDATE data_table SET Difficulty = difficulty WHERE ID = id
    public void updateDifficulty(int id, int difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5  + " = " + difficulty
                        + " WHERE " + COL1 + " = " + id;
        db.execSQL(query);
    }

    // SELECT ID FROM data_table WHERE IsTeacher = 1 LIMIT 9
    public boolean noTeacherWords(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + COL1 +  " FROM " + TABLE_NAME + " WHERE " + COL4 + " = 1 LIMIT " + 9;
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() < 9) {
            return true;
        }
        mCursor.close();
        return false;
    }

    private boolean checkForTableEmpty(SQLiteDatabase db){
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() == 0) {
            return true;
        }
        mCursor.close();
        return false;
    }

    //Below functions are to extract the data from the database and display
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    // SELECT * FROM data_table ORDER BY RANDOM() LIMIT 9
    public Cursor getDataForNormalMode(int numberOfWords){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT " + numberOfWords;
        return db.rawQuery(query, null);
    }

    //  SELECT * FROM (SELECT * FROM data_table ORDER BY Difficulty ASC LIMIT 100) ORDER BY RANDOM() LIMIT 9
    public Cursor getDataForDifficultyMode(int numberOfWords){
        SQLiteDatabase db = this.getWritableDatabase();
        String subQuery = "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + COL5  + " ASC LIMIT " + NUMBER_OF_TOP_DIFFICULT_WORDS;
        String query = "SELECT * FROM ( " + subQuery + " ) ORDER BY RANDOM() LIMIT " + numberOfWords;
        return db.rawQuery(query, null);
    }

    // SELECT * FROM data_table WHERE IsTeacher = 1 ORDER BY RANDOM() LIMIT 9
    public Cursor getDataForTeacherMode(int numberOfWords){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL4 + " = 1 "
                            + " ORDER BY RANDOM() LIMIT " + numberOfWords;
        return db.rawQuery(query, null);
    }

    // Function for WordBankFragment
    public List<WordPair> getAllWordPairs() {
        Cursor cursor = getData();
        List<WordPair> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow(DBController.COL1);
            int id = cursor.getInt(index);
            index = cursor.getColumnIndexOrThrow(DBController.COL2);
            String word1 = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(DBController.COL3);
            String word2 = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(DBController.COL5);
            int difficulty = cursor.getInt(index);
            WordPair pair = new WordPair(id, word1, word2, difficulty);
            data.add(pair);
        }
        return data;
    }
}