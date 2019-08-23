package com.bignerdranch.android.sudokuapp.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordList implements Parcelable {
    // words is used to hold numberOfWords WordPairs.
    private final WordPair[] words;

    // wordValues is used to keep track of values associated with each
    //  WordPair in words (value = position in words)
    private final HashMap<String, Integer> wordValues;

    // Number of words on the board
    private int numberOfWords = 9;

    // Default constructor for JUnit tests
    public WordList() {
        String englishLabels[] = new String[] {
                "Hello", "Goodbye", "Thank you", "Sorry",
                "Excuse me", "Please", "Of course", "Yes", "No",
        };
        String japaneseLabels[] = new String[] {
                "こんにちは", "さようなら", "ありがとう" , "ごめんなさい",
                "すみません", "お願いします", "もちろん", "はい", "いいえ",
        };
        words = new WordPair[9];
        wordValues = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            words[i] = new WordPair(i, englishLabels[i], japaneseLabels[i], 0);
            wordValues.put(englishLabels[i], i);
            wordValues.put(japaneseLabels[i], i);
        }
    }

    // For FlashCardWords
    public WordList(List<WordPair> wordPairArrayList) {
        int size = wordPairArrayList.size();
        words = new WordPair[size];
        wordValues = new HashMap<>();
        for (int i = 0; i < size; i++) {
            WordPair wp = wordPairArrayList.get(i);
            words[i] = wp;
            wordValues.put(wp.getLangA(), i);
            wordValues.put(wp.getLangB(), i);
        }
    }

    public WordList(Cursor cursor, int numberOfWords) {
        this.numberOfWords = numberOfWords;

        List<WordPair> dbData = new ArrayList<>();
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
            dbData.add(pair);
        }

        words = new WordPair[numberOfWords];
        wordValues = new HashMap<>();
        for (int i = 0; i < numberOfWords; i++) {
            WordPair pair = dbData.get(i);
            words[i] = pair;
            wordValues.put(pair.getLangA(), i);
            wordValues.put(pair.getLangB(), i);
        }
    }

    // wordPairForValue returns a WordPair for a particular value
    // (value == index of WordPair in words)
    public WordPair wordPairForValue(int i) {
        return words[i];
    }

    // valueForWord returns the value associated with a particular word
    public int valueForWord(String word) {
        Integer value = wordValues.get(word);
        if (value == null)
            value = -1;
        return value;
    }

    public void setDifficulty(int value, int diffVal){
        wordPairForValue(value).changeDifficulty(diffVal);
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(words, 0);
        dest.writeSerializable(wordValues);
    }

    @SuppressWarnings("unchecked")
    private WordList(Parcel source) {
        words = source.createTypedArray(WordPair.CREATOR);
        wordValues = (HashMap) source.readSerializable();
    }

    public static final Parcelable.Creator<WordList> CREATOR = new Parcelable.Creator<WordList>() {
        @Override
        public WordList createFromParcel(Parcel source) {return new WordList(source);}

        @Override
        public WordList[] newArray(int size) {return new WordList[size];
        }
    };
}
