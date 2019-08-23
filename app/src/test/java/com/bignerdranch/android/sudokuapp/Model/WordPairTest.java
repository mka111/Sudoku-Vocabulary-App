package com.bignerdranch.android.sudokuapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WordPairTest {
    WordPair pair;

    @Before
    public void setUp() throws Exception {
        pair = new WordPair(0, "Hello", "こんにちは", 0);
    }

    @Test
    public void testGetLang() {
        assertEquals("Hello", pair.getLangA());
        assertEquals("こんにちは", pair.getLangB());
        assertFalse("Hello".equals(pair.getLangB()));
        assertFalse("こんにちは".equals(pair.getLangA()));
    }

    @Test
    public void testID() {
        assertEquals(0, pair.getId());
    }

    @Test
    public void testDifficulty() {
        assertEquals(0, pair.getDifficulty());
        pair.changeDifficulty(1);
        assertEquals(1, pair.getDifficulty());
        pair.changeDifficulty(20);
        assertEquals(21, pair.getDifficulty());
        pair.changeDifficulty(-1);
        assertEquals(20, pair.getDifficulty());
    }
}