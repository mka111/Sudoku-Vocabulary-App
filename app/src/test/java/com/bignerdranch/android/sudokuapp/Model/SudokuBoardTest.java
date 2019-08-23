package com.bignerdranch.android.sudokuapp.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SudokuBoardTest {

    SudokuBoard board;
    int[] fullBoardWithValues;

    @Before
    public void setUp() throws Exception {
        board = new SudokuBoard(9);
        fullBoardWithValues = board.getFullBoardWithValues();
    }

    @Test
    public void testValidBoardWithValidValues() {
        // board should hold 81 values
        assertEquals(81, fullBoardWithValues.length);
        // All values between 0-8
        for (int i = 0; i < fullBoardWithValues.length; i++) {
            assertTrue((0 <= fullBoardWithValues[i]) &&
                    (fullBoardWithValues[i] <= 8));
        }
    }

    @Test
    public void testUniqueRowValues() {
        // Check rows have unique values between 0 and 8 inclusive
        Set<Integer> row = new HashSet<Integer>();
        for (int i = 0; i < fullBoardWithValues.length; i++) {
            if (i % 9 == 0) {
                // new row
                row = new HashSet<Integer>();
            }
            assertTrue(row.add(fullBoardWithValues[i]));
        }
    }

    @Test
    public void testUniqueColumnValues() {
        // Check columns have unique values between 0 and 8 inclusive
        Set<Integer> column = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i + (j*9);
                assertTrue(column.add(fullBoardWithValues[index]));
            }
            column = new HashSet<Integer>();
        }
    }

    @Test
    public void testUniqueSquareValues() {
        ArrayList<List<Integer>> squareCollection = new ArrayList<>();
        List<Integer> square1 = Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20);
        List<Integer> square2 = Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23);
        List<Integer> square3 = Arrays.asList(6, 7, 8, 15, 16, 17, 24, 25, 26);

        List<Integer> square4 = Arrays.asList(27, 28, 29, 36, 37, 38, 45, 46, 47);
        List<Integer> square5 = Arrays.asList(30, 31, 32, 39, 40, 41, 48, 49, 50);
        List<Integer> square6 = Arrays.asList(33, 34, 35, 42, 43, 44, 51, 52, 53);

        List<Integer> square7 = Arrays.asList(54, 55, 56, 63, 64, 65, 72, 73, 74);
        List<Integer> square8 = Arrays.asList(57, 58, 59, 66, 67, 68, 75, 76, 77);
        List<Integer> square9 = Arrays.asList(60, 61, 62, 69, 70, 71, 78, 79, 80);

        squareCollection.add(square1);
        squareCollection.add(square2);
        squareCollection.add(square3);
        squareCollection.add(square4);
        squareCollection.add(square5);
        squareCollection.add(square6);
        squareCollection.add(square7);
        squareCollection.add(square8);
        squareCollection.add(square9);

        Set<Integer> squareValues = new HashSet<Integer>();
        for (List<Integer> square : squareCollection) {
            for (Integer index : square) {
                assertTrue(squareValues.add(fullBoardWithValues[index]));
            }
            squareValues = new HashSet<Integer>();
        }
    }

    @Test
    public void testGetPreFilledSpots() {
        // All values in preFrilledSpots should be in range 0 to 80 inclusive
        Set<Integer> preFilledSpots = board.getPreFilledSpots();
        for (Integer i : preFilledSpots) {
            assertTrue((0 <= i) && (i <= 80));
        }
    }
}