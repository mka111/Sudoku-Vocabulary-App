package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WordPairParcelableTest {
    @Test
    public void testParcelable() {
        WordPair wp = new WordPair(0,"Hello", "こんにちは",0);
        Parcel parcel = Parcel.obtain();
        wp.writeToParcel(parcel, wp.describeContents());
        parcel.setDataPosition(0);
        WordPair createdFromParcel = WordPair.CREATOR.createFromParcel(parcel);
        assertEquals(wp.getLangA(), createdFromParcel.getLangA());
        assertEquals(wp.getLangB(), createdFromParcel.getLangB());
    }
}
