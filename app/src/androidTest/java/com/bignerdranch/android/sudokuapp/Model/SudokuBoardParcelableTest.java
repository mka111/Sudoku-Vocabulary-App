package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SudokuBoardParcelableTest {
    @Test
    public void testParcelable() {
        SudokuBoard board = new SudokuBoard(4);
        Parcel parcel = Parcel.obtain();
        board.writeToParcel(parcel, board.describeContents());
        parcel.setDataPosition(0);
        SudokuBoard createdFromParcel = SudokuBoard.CREATOR.createFromParcel(parcel);
        assertEquals(board.getBoardLength(), createdFromParcel.getBoardLength());
        assertArrayEquals(board.getFullBoardWithValues(), createdFromParcel.getFullBoardWithValues());
        assertEquals(board.getPreFilledSpots(), createdFromParcel.getPreFilledSpots());
        assertEquals(board.getSize(), createdFromParcel.getSize());
    }
}