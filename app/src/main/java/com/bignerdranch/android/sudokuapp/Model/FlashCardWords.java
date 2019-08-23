package com.bignerdranch.android.sudokuapp.Model;

import android.content.Context;
import android.util.Log;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Flashcard words is a singleton object used solely to hold
//  a list of words user has selected to practice in WordBankFragment
public class FlashCardWords {
    private static FlashCardWords sFlashCardWords;

    private List<WordPair> mWordPairs;
    private Context mContext;

    public static FlashCardWords get(Context context) {
        if (sFlashCardWords == null) {
            sFlashCardWords = new FlashCardWords(context);
        }
        return sFlashCardWords;
    }

    private FlashCardWords(Context context) {
        mWordPairs = new ArrayList<>();
        mContext = context;
    }

    public void addWordPair(WordPair wp) {
        // Ensure that wp isn't already in mWordPairs
        for (WordPair wordPair : mWordPairs) {
            if (wordPair.getId() == wp.getId()) {
                return;
            }
        }
        mWordPairs.add(wp);
    }

    public void removeWordPair(WordPair wp) {
        for (int i = 0; i < mWordPairs.size(); i++) {
            if (mWordPairs.get(i).getId() == wp.getId()) {
                mWordPairs.remove(i);
                return;
            }
        }
    }

    public void clearWordPairs() {
        mWordPairs.clear();
    }

    // Valid size values: 4, 6, 9, or 12
    public WordList getWordListForSize(int size) {
        if (mWordPairs.size() == size) {
            return new WordList(mWordPairs);
        }
        List<WordPair> wordPairList = new ArrayList<>();
        if (mWordPairs.size() < size) {
            int i = 0;
            for (; i < mWordPairs.size(); i++) {
                wordPairList.add(mWordPairs.get(i));
            }
            // Get random words to fill rest of list
            DBController db = new DBController(mContext);
            List<WordPair> dbWordPairs = db.getAllWordPairs();
            Collections.shuffle(dbWordPairs);
            for (int j = 0; i < size && j < dbWordPairs.size(); j++) {
                boolean alreadyPresent = false;
                WordPair dbWord = dbWordPairs.get(j);
                for (int k = 0; k < mWordPairs.size(); k++) {
                    if (dbWord.getId() == mWordPairs.get(k).getId()) {
                        alreadyPresent = true;
                        break;
                    }
                }
                if (!alreadyPresent) {
                    wordPairList.add(dbWordPairs.get(j));
                    i++;
                }
            }
        } else { // mWordsPairs > size
            Collections.shuffle(mWordPairs);
            for (int i = 0; i < size; i++) {
                wordPairList.add(mWordPairs.get(i));
            }
        }
        return new WordList(wordPairList);
    }
}
