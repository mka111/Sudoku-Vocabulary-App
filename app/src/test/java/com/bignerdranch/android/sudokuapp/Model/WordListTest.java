package com.bignerdranch.android.sudokuapp.Model;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordListTest {
    private WordPair[] wordPairs;
    private WordList wordList;
    private DBController db;
    @Before
    public void setUp() throws Exception {
        // TODO: Fix Unit test - initialize db somehow
        // Can't initialize without a Context
        // DBController db = new DBController(context);
        // Use default constructor for now
        wordList = new WordList();
        wordPairs = new WordPair[9];
        for (int i = 0; i < 9; i++)
            wordPairs[i] = wordList.wordPairForValue(i);
    }

    @Test
    public void testWordPairForValue() {
        for (int i = 0; i < 9; i++) {
            assertEquals(wordPairs[i].getLangA(), wordList.wordPairForValue(i).getLangA());
            assertEquals(wordPairs[i].getLangB(), wordList.wordPairForValue(i).getLangB());
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testWordPairForValueInvalidIndexLessThanZero() {
        // Valid indexes for wordPairForValue: 0-8 inclusive
        wordList.wordPairForValue(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testWordPairForValueInvalidIndexGreaterThanNumberOfWords() {
        // Valid indexes for wordPairForValue: 0-(numberOfWords - 1) inclusive
        int badIndex = wordList.getNumberOfWords() + 1;
        wordList.wordPairForValue(badIndex);
    }

    @Test
    public void testValueForWordFound() {
        for (int i = 0; i < wordList.getNumberOfWords(); i++) {
            assertEquals(i, wordList.valueForWord(wordPairs[i].getLangA()));
            assertEquals(i, wordList.valueForWord(wordPairs[i].getLangB()));
        }
    }

    @Test
    public void testValueForWordNotFound() {
        // valueForWord should return -1 if word is not found
        assertEquals(-1, wordList.valueForWord("I shouldn't exist"));
    }

    @Test
    public void testSetDifficultyWithValidIndex() {
        wordList.setDifficulty(0, 1);
        WordPair wp = wordList.wordPairForValue(0);
        assertEquals(1, wp.getDifficulty());
        wp = wordList.wordPairForValue(1);
        assertEquals(0, wp.getDifficulty());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testSetDifficultyInvalidIndexLessThanZero() {
        wordList.setDifficulty(-1,1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testSetDifficultyInvalidIndexGreaterThanEight() {
        int badIndex = wordList.getNumberOfWords() + 1;
        wordList.setDifficulty(badIndex,2);
    }
}