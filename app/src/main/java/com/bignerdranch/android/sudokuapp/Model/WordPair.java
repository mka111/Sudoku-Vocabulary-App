package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

// WordPair is used to hold a reference to two words in Language A and Language B
// mLangA translated == mLangB (and vice-versa)
// For iteration 1, LangA = English; LangB = Japanese
public class WordPair implements Parcelable {
    private int id;
    private String mLangA;
    private String mLangB;
    private int difficulty;

    public WordPair(int id, String langA, String langB, int difficulty) {
        this.id = id;
        mLangA = langA;
        mLangB = langB;
        this.difficulty = difficulty;
    }
    public String getLangA() {
        return mLangA;
    }
    public String getLangB() {
        return mLangB;
    }
    public int getId() {
        return id;
    }
    public int getDifficulty() {
        return difficulty;
    }

    public void changeDifficulty(int val) {
        this.difficulty += val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mLangA);
        dest.writeString(mLangB);
        dest.writeInt(difficulty);
    }

    private WordPair(Parcel source) {
        id = source.readInt();
        mLangA = source.readString();
        mLangB = source.readString();
        difficulty = source.readInt();
    }

    public static final Parcelable.Creator<WordPair> CREATOR = new Parcelable.Creator<WordPair>() {
        @Override
        public WordPair createFromParcel(Parcel source) {
            return new WordPair(source);
        }

        @Override
        public WordPair[] newArray(int size) {
            return new WordPair[size];
        }
    };
}
