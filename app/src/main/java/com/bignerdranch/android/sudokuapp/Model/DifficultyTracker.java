package com.bignerdranch.android.sudokuapp.Model;

import android.support.annotation.NonNull;

public class DifficultyTracker {

    private static final int INC_DIFFICULTY = 1;
    private static final int DEC_DIFFICULTY = -1;
    private int[] fullBoardWithValues;
    private WordList wordList;

    public DifficultyTracker(@NonNull SudokuBoard board, WordList wordList)
    {
        this.fullBoardWithValues = board.getFullBoardWithValues();
        this.wordList = wordList;
    }

    // if user is wrong, difficulty of the word is increased by 1, otherwise it is decreased by 1
    public void countDifficulty(String word, int position)
    {
        int wordValue = wordList.valueForWord(word);
        if (wordValue == fullBoardWithValues[position]) {
            wordList.setDifficulty(wordValue, DEC_DIFFICULTY);
        } else {
            wordList.setDifficulty(wordValue, INC_DIFFICULTY);
        }
    }

}
