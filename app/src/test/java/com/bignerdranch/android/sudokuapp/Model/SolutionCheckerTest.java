package com.bignerdranch.android.sudokuapp.Model;

import com.bignerdranch.android.sudokuapp.Controller.DBController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionCheckerTest {
    private WordList words;
    private SudokuBoard board;
    private SolutionChecker checker;
    private DBController db;
    private final static int NUMBER_OF_WORDS = 9;

    @Before
    public void setUp() throws Exception {
        // TODO: Fix Unit test - initialize db somehow
        // Can't initialize without a Context
        // db = new DBController(context)
        // words= new WordList(db.getData(),9);
        // Use defualt constructor for now
        words = new WordList();
        board = new SudokuBoard(9);
        checker = new SolutionChecker(board, words, NUMBER_OF_WORDS, board.getBoardLength());
    }

    @Test (expected = NullPointerException.class)
    public void nonExistentBoard() {
        SolutionChecker solutionchecker = new SolutionChecker(null, words, NUMBER_OF_WORDS, board.getBoardLength());
    }

    @Test
    public void testOneAnswerBoard() {
        checker.addUserAnswer(0, words.wordPairForValue(0).getLangA());
        assertFalse(checker.checkAnswers());
    }

    @Test
    public void testIncompleteBoard() {
        for (int i =2; i < board.getFullBoardWithValues().length; i++) {
            int value = board.getFullBoardWithValues()[i];
            WordPair pair = words.wordPairForValue(value);
            checker.addUserAnswer(i, pair.getLangA());
        }
        checker.addUserAnswer(0, "I don't exist");
        checker.addUserAnswer(1, "I don't exist");
        assertFalse(checker.checkAnswers());
    }

    @Test
    public void testCompleteBoardCorrectAnswersLangA() {
        for (int i = 0; i < board.getFullBoardWithValues().length; i++) {
            int value = board.getFullBoardWithValues()[i];
            WordPair pair = words.wordPairForValue(value);
            checker.addUserAnswer(i, pair.getLangA());
        }
        assertTrue(checker.checkAnswers());
    }

    @Test
    public void testCompleteBoardCorrectAnswersLangB() {
        for (int i = 0; i < board.getFullBoardWithValues().length; i++) {
            int value = board.getFullBoardWithValues()[i];
            WordPair pair = words.wordPairForValue(value);
            checker.addUserAnswer(i, pair.getLangB());
        }
        assertTrue(checker.checkAnswers());
    }

    @Test
    public void testCompleteBoardThenRemoveAnswer() {
        for (int i = 0; i < board.getFullBoardWithValues().length; i++) {
            int value = board.getFullBoardWithValues()[i];
            WordPair pair = words.wordPairForValue(value);
            checker.addUserAnswer(i, pair.getLangA());
        }
        checker.deleteUserAnswer(0);
        assertFalse(checker.checkAnswers());
    }

    @Test
    public void testCompleteBoardOneIncorrectAnswer() {
        for (int i = 1; i < board.getFullBoardWithValues().length; i++) {
            int value = board.getFullBoardWithValues()[i];
            WordPair pair = words.wordPairForValue(value);
            checker.addUserAnswer(i, pair.getLangA());
        }
        checker.addUserAnswer(0, words.wordPairForValue(1).getLangA());
        assertFalse(checker.checkAnswers());
    }

    @Test
    public void nonExistentWord() {
        checker.addUserAnswer(0, "I don't exist");
        assertFalse(checker.checkAnswers());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void invalidIndex() {
        checker.addUserAnswer(-1, "blah");
        assertFalse(checker.checkAnswers());
    }

    @Test
    public void testIncorrectIndices() {
        int correctValue = board.getFullBoardWithValues()[0];
        // TODO: Change 9 to whatever dimension board is
        int incorrectValue = (correctValue + 1) % 9;
        WordPair incorrectWordPair = words.wordPairForValue(incorrectValue);
        checker.addUserAnswer(0, incorrectWordPair.getLangA());
        assertFalse(checker.getIncorrectIndices().isEmpty());
        assertTrue(checker.getIncorrectIndices().contains(0));
        checker.deleteUserAnswer(0);
        assertTrue(checker.getIncorrectIndices().isEmpty());
        assertFalse(checker.getIncorrectIndices().contains(0));
        WordPair correctWordPair = words.wordPairForValue(correctValue);
        checker.addUserAnswer(0, correctWordPair.getLangA());
        assertTrue(checker.getIncorrectIndices().isEmpty());
        assertFalse(checker.getIncorrectIndices().contains(0));
    }

    @Test
    public void testEquality() {
        // Another checker with same board and same words should be equal
        SolutionChecker otherChecker = new SolutionChecker(board, words, NUMBER_OF_WORDS, board.getBoardLength());
        assertTrue(checker.equals(otherChecker));
        // Same checker with new value added should not be equal
        otherChecker.addUserAnswer(0, words.wordPairForValue(0).getLangA());
        assertFalse(checker.equals(otherChecker));
        // Adding same value to original checker should be equal to other checker
        checker.addUserAnswer(0, words.wordPairForValue(0).getLangA());
        assertTrue(checker.equals(otherChecker));
        // After removing value from other checker, original and other checker should no
        //  longer be equal
        otherChecker.deleteUserAnswer(0);
        assertFalse(checker.equals(otherChecker));
        // checker should not equal another type of object
        assertFalse(checker.equals(words));
        // checker should not be equal to null
        assertFalse(checker.equals(null));
        // checker should be equal to itself
        assertTrue(checker.equals(checker));
    }
}
