package com.bignerdranch.android.sudokuapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DifficultyTrackerTest {
    private WordList wordList;
    private SudokuBoard board;
    private int zeroPos;

    @Before
    public void setUp() throws Exception {
//        wordList = new WordList(new Cursor() {
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public int getPosition() {
//                return 0;
//            }
//
//            @Override
//            public boolean move(int offset) {
//                return false;
//            }
//
//            @Override
//            public boolean moveToPosition(int position) {
//                return false;
//            }
//
//            @Override
//            public boolean moveToFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToLast() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToNext() {
//                return false;
//            }
//
//            @Override
//            public boolean moveToPrevious() {
//                return false;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean isBeforeFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isAfterLast() {
//                return false;
//            }
//
//            @Override
//            public int getColumnIndex(String columnName) {
//                return 0;
//            }
//
//            @Override
//            public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
//                return 0;
//            }
//
//            @Override
//            public String getColumnName(int columnIndex) {
//                return null;
//            }
//
//            @Override
//            public String[] getColumnNames() {
//                return new String[0];
//            }
//
//            @Override
//            public int getColumnCount() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBlob(int columnIndex) {
//                return new byte[0];
//            }
//
//            @Override
//            public String getString(int columnIndex) {
//                return null;
//            }
//
//            @Override
//            public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
//
//            }
//
//            @Override
//            public short getShort(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public int getInt(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public long getLong(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public float getFloat(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public double getDouble(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public int getType(int columnIndex) {
//                return 0;
//            }
//
//            @Override
//            public boolean isNull(int columnIndex) {
//                return false;
//            }
//
//            @Override
//            public void deactivate() {
//
//            }
//
//            @Override
//            public boolean requery() {
//                return false;
//            }
//
//            @Override
//            public void close() {
//
//            }
//
//            @Override
//            public boolean isClosed() {
//                return false;
//            }
//
//            @Override
//            public void registerContentObserver(ContentObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterContentObserver(ContentObserver observer) {
//
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void setNotificationUri(ContentResolver cr, Uri uri) {
//
//            }
//
//            @Override
//            public Uri getNotificationUri() {
//                return null;
//            }
//
//            @Override
//            public boolean getWantsAllOnMoveCalls() {
//                return false;
//            }
//
//            @Override
//            public void setExtras(Bundle extras) {
//
//            }
//
//            @Override
//            public Bundle getExtras() {
//                return null;
//            }
//
//            @Override
//            public Bundle respond(Bundle extras) {
//                return null;
//            }
//        }, 9);
        // Use default constructor for now
        wordList = new WordList();
        board = new SudokuBoard(9);
        for (int i = 0; i < 9; i++) {
            if (board.getFullBoardWithValues()[i] == 0) {
                zeroPos = i;
                break;
            }
        }

    }

    @Test (expected = NullPointerException.class)
    public void nonExistentBoard() {
        DifficultyTracker difficultyTracker = new DifficultyTracker(null, wordList);
    }

    @Test
    public void countDifficulty() {
        DifficultyTracker difficultyTracker = new DifficultyTracker(board, wordList);
        WordPair wordZero = wordList.wordPairForValue(0);
        WordPair wordNonZero = wordList.wordPairForValue(1);
        String wordZeroStr = wordZero.getLangA();
        String wordNonZeroStr = wordNonZero.getLangA();
        int wordZeroDiff = wordZero.getDifficulty();
        int wordNonZeroDiff = wordNonZero.getDifficulty();

        difficultyTracker.countDifficulty(wordZeroStr, zeroPos);
        difficultyTracker.countDifficulty(wordNonZeroStr, zeroPos);

        // difficulty of the correct word is decreased by 1
        assertEquals(wordZeroDiff - 1, wordZero.getDifficulty());
        // difficulty of the wrong word is increased by 1
        assertEquals(wordNonZeroDiff + 1, wordNonZero.getDifficulty());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void wrongWordInDifficultyCounter() {
        DifficultyTracker difficultyTracker = new DifficultyTracker(board, wordList);
        difficultyTracker.countDifficulty("check", zeroPos);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void wrongPositionInDifficultyCounter() {
        DifficultyTracker difficultyTracker = new DifficultyTracker(board, wordList);
        WordPair wordZero = wordList.wordPairForValue(0);
        String wordZeroStr = wordZero.getLangA();
        difficultyTracker.countDifficulty(wordZeroStr, -1);
    }
}