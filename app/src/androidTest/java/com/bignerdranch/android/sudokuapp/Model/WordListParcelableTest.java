package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WordListParcelableTest {
    @Test
    public void testParcelable() {
        DBController db = new DBController(InstrumentationRegistry.getTargetContext());
        WordList wordList = new WordList(db.getData(), 9);
        Parcel parcel = Parcel.obtain();
        wordList.writeToParcel(parcel, wordList.describeContents());
        parcel.setDataPosition(0);
        WordList createdFromParcel = WordList.CREATOR.createFromParcel(parcel);
        for (int i = 0; i < 9; i++) {
            assertEquals(wordList.wordPairForValue(i).getLangA(),
                    createdFromParcel.wordPairForValue(i).getLangA());
            assertEquals(wordList.wordPairForValue(i).getLangB(),
                    createdFromParcel.wordPairForValue(i).getLangB());
        }
    }
}