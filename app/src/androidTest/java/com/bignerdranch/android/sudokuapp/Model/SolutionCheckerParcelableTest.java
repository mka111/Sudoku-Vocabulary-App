package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SolutionCheckerParcelableTest {
    @Test
    public void testParcelable() {
        int numberOfWords = 9;
        SudokuBoard board = new SudokuBoard(numberOfWords);
        DBController db = new DBController(InstrumentationRegistry.getTargetContext());
        WordList words = new WordList(db.getData(), 9);
        SolutionChecker checker = new SolutionChecker(board, words, numberOfWords, board.getBoardLength());
        Parcel parcel = Parcel.obtain();
        checker.addUserAnswer(0, words.wordPairForValue(0).getLangA());
        checker.writeToParcel(parcel, checker.describeContents());
        parcel.setDataPosition(0);
        SolutionChecker createdFromParcel = SolutionChecker.CREATOR.createFromParcel(parcel);
        assertTrue(checker.equals(createdFromParcel));
        createdFromParcel.deleteUserAnswer(0);
        assertFalse(checker.equals(createdFromParcel));
        checker.deleteUserAnswer(0);
        assertTrue(checker.equals(createdFromParcel));
    }
}