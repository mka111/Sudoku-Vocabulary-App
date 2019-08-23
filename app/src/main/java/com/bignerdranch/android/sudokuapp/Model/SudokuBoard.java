package com.bignerdranch.android.sudokuapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SudokuBoard implements Parcelable {
    // fullBoardWith values holds the values 0-8 for each position
    //  in the board. (0 => 1, 1 => 2, ..., 8 => 9; from traditional sudoku board)
    private int[] fullBoardWithValues;

    // preFilledSpots holds the indexes for the spots already filled in
    //  in the board.
    private HashSet<Integer> preFilledSpots;

    private int size;

    public SudokuBoard(int size, int level) {
        this.size = size;

        switch (size) {
            case 4: setRandom4x4Board(level); break;
            case 6: setRandom6x6Board(level); break;
            case 12: setRandom12x12Board(level); break;
            default: setRandom9x9Board(level);
        }
    }

    public int[] getFullBoardWithValues() {
        return fullBoardWithValues;
    }

    public Set<Integer> getPreFilledSpots() {
        return preFilledSpots;
    }

    public int getSize() {
        return size;
    }

    public int getBoardLength() {
        return fullBoardWithValues.length;
    }

    private void setRandom9x9Board(int levels) {
        // For iteration 1: SudokuBoard maps to intermediate example found here:
        //  https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html
        // 0 => 1, 1 => 2, ..., 8 => 9 from board
        Random rand = new Random();
        int n = rand.nextInt(6);
        switch(n) {
            case 0:
                fullBoardWithValues = new int[] {
                        0, 1, 2, 5, 6, 7, 8, 3, 4,
                        4, 7, 3, 1, 2, 8, 6, 5, 0,
                        8, 5, 6, 0, 3, 4, 2, 1, 7,
                        2, 6, 1, 3, 5, 0, 4, 7, 8,
                        5, 8, 0, 4, 7, 2, 1, 6, 3,
                        3, 4, 7, 6, 8, 1, 5, 0, 2,
                        7, 2, 5, 8, 1, 3, 0, 4, 6,
                        1, 0, 8, 7, 4, 6, 3, 2, 5,
                        6, 3, 4, 2, 0, 5, 7, 8, 1,
                };
                break;
            case 1:
                fullBoardWithValues = new int[] {
                        2, 3, 7, 4, 0, 8, 5, 1, 6,
                        4, 5, 8, 1, 6, 3, 0, 2, 7,
                        6, 0, 1, 2, 7, 5, 8, 4, 3,
                        8, 6, 0, 7, 4, 1, 2, 3, 5,
                        7, 4, 5, 8, 3, 2, 6, 0, 1,
                        3, 1, 2, 0, 5, 6, 4, 7, 8,
                        5, 2, 6, 3, 1, 0, 7, 8, 4,
                        1, 8, 4, 6, 2, 7, 3, 5, 0,
                        0, 7, 3, 5, 8, 4, 1, 6, 2,
                };
                break;
            case 2:
                fullBoardWithValues = new int[] {
                        0, 1, 6, 2, 7 ,5 ,3 ,4 ,8,
                        3, 2, 5, 8, 4, 0, 6, 1, 7,
                        8, 7, 4, 3, 6, 1, 2, 0, 5,
                        4, 6, 7, 5, 0, 3, 8, 2, 1,
                        2, 0, 3, 6, 1, 8, 5, 7, 4,
                        5, 8, 1, 7, 2, 4, 0, 6, 3,
                        1, 5, 0, 4, 8, 6, 7, 3, 2,
                        7, 4, 8, 0, 3, 2, 1, 5, 6,
                        6, 3, 2, 1, 5, 7, 4, 8, 0,
                };
                break;
            case 3:
                fullBoardWithValues = new int[] {
                        4, 6, 1, 7, 5, 0, 3, 8, 2,
                        3, 7, 0, 4, 8, 2, 6, 1, 5,
                        2, 5, 8, 1, 3, 6, 7, 0, 4,
                        5, 8, 6, 3, 0, 7, 2, 4, 1,
                        7, 4, 3, 2, 1, 8, 0, 5, 6,
                        1, 0, 2, 6, 4, 5, 8, 3, 7,
                        0, 1, 7, 5, 6, 3, 4, 2, 8,
                        8, 2, 4, 0, 7, 1, 5, 6, 3,
                        6, 3, 5, 8, 2, 4, 1, 7, 0,
                };
                break;
            case 4:
                fullBoardWithValues = new int[] {
                        8, 4, 1, 3, 0, 5, 6, 7, 2,
                        2, 7, 6, 4, 1, 8, 3, 5, 0,
                        0, 3, 5, 2, 7, 6, 4, 8, 1,
                        1, 5, 0, 6, 2, 4, 8, 3, 7,
                        3, 8, 2, 1, 5, 7, 0, 4, 6,
                        7, 6, 4, 8, 3, 0, 1, 2, 5,
                        5, 0, 8, 7, 4, 1, 2, 6, 3,
                        4, 2, 7, 0, 6, 3, 5, 1, 8,
                        6, 1, 3, 5, 8, 2, 7, 0, 4,
                };
                break;
            case 5:
                fullBoardWithValues = new int[] {
                        1, 5, 0, 6, 8, 4, 2, 3, 7,
                        8, 3, 2, 5, 0, 7, 4, 6, 1,
                        4, 6, 7, 2, 1, 3, 5, 8, 0,
                        5, 8, 3, 7, 2, 0, 6, 1, 4,
                        7, 2, 1, 4, 5, 6, 8, 0, 3,
                        0, 4, 6, 1, 3, 8, 7, 5, 2,
                        6, 0, 4, 3, 7, 5, 1, 2, 8,
                        3, 1, 5, 8, 4, 2, 0, 7, 6,
                        2, 7, 8, 0, 6, 1, 3, 4, 5,
                };
                break;

        }
        preFilledSpots = new HashSet<Integer>();
        switch(levels){
            case 0:
                preFilledSpots.addAll(Arrays.asList(
                        1, 3, 4, 5, 9, 11, 12, 14, 13, 15,
                        18, 20, 22, 23, 25, 27, 28, 32, 33,
                        36, 39, 40, 42, 44, 47, 49, 52, 53,
                        56, 58, 60, 61, 62, 64, 65, 66, 67,
                        69, 70, 71, 73, 74, 75, 77, 79)
                );
                break;
            case 1:
                preFilledSpots.addAll(Arrays.asList(
                        1, 3, 5, 9, 10, 14, 15, 22,
                        27, 28, 33, 36, 42, 44, 47,
                        49, 52, 61, 62, 64, 65, 66,
                        67, 69, 70, 71, 75, 77, 79)
                );
                break;
            case 2:
                preFilledSpots.addAll(Arrays.asList(
                        1, 5, 9, 10, 12, 14, 15, 22,
                        27, 28, 33, 36, 44, 47, 52,
                        53, 70, 77, 79)
                );
                break;

        }


        //for testing purposes only
//        preFilledSpots.addAll(Arrays.asList(
//                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
//                24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
//                41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
//                61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
//        );
    }

    // TODO: Make random following methods random
    private void setRandom4x4Board(int levels) {
        fullBoardWithValues = new int[] {
                1, 0, 3, 2,
                3, 2, 1, 0,
                2, 1, 0, 3,
                0, 3, 2, 1,
        };
        preFilledSpots = new HashSet<Integer>();
        switch(levels){
            case 0:
                preFilledSpots.addAll(Arrays.asList(
                        0, 1, 4, 5, 6, 11, 12, 13, 2, 3)
                );
                break;
            case 1:
                preFilledSpots.addAll(Arrays.asList(
                        0, 1, 5, 6, 11, 12, 8, 15)
                );
                break;
            case 2:
                preFilledSpots.addAll(Arrays.asList(
                        0, 4, 5, 10, 15)
                );
                break;

        }

    }

    private void setRandom6x6Board(int levels) {
        fullBoardWithValues = new int[] {
                5, 4, 1, 3, 2, 0,
                2, 0, 3, 5, 1, 4,
                3, 2, 5, 0, 4, 1,
                0, 1, 4, 2, 5, 3,
                1, 3, 2, 4, 0, 5,
                4, 5, 0, 1, 3, 2,
        };
        preFilledSpots = new HashSet<Integer>();
        switch(levels){
            case 0:
                preFilledSpots.addAll(Arrays.asList(
                        1, 4, 8, 9, 12, 15, 16, 19, 18,
                        23, 20, 22 ,33, 29, 34, 35, 11,
                        27, 31, 11, 5, 0, 6, 14)
                );
                break;
            case 1:
                preFilledSpots.addAll(Arrays.asList(
                        1, 4, 8, 9, 12, 15, 16, 19, 18,
                        23, 20, 33, 34, 35, 27, 31, 11)
                );
                break;
            case 2:
                preFilledSpots.addAll(Arrays.asList(
                        1, 5, 8, 9, 12, 16, 18,
                        23, 26, 33, 34)
                );
                break;

        }

    }

    private void setRandom12x12Board(int levels) {
        fullBoardWithValues = new int[] {
                0, 8, 5, 6, 10, 7, 4, 3, 1, 9, 11, 2,
                4, 1, 9, 10, 5, 11, 0, 2, 6, 7, 3, 8,
                11, 7, 2, 3, 1, 6, 9, 8, 10, 0, 5, 4,
                1, 6, 8, 0, 3, 9, 7, 4, 11, 5, 2, 10,
                10, 11, 7, 9, 2, 0, 5, 1, 4, 8, 6, 3,
                2, 4, 3, 5, 8, 10, 6, 11, 7, 1, 9, 0,
                8, 9, 1, 11, 7, 2, 3, 10, 5, 4, 0, 6,
                5, 2, 0, 7, 11, 4, 8, 6, 9, 3, 10, 1,
                3, 10, 6, 4, 0, 5, 1, 9, 2, 11, 8, 7,
                9, 0, 11, 1, 6, 3, 10, 7, 8, 2, 4, 5,
                7, 3, 10, 2, 4, 8, 11, 5, 0, 6, 1, 9,
                6, 5, 4, 8, 9, 1, 2, 0, 3, 10, 7, 11,
        };
        preFilledSpots = new HashSet<Integer>();
        switch(levels){
            case 0:
                preFilledSpots.addAll(Arrays.asList(
                        0, 1, 4, 5, 9,
                        12, 13, 14, 17,
                        18, 21, 22, 25, 26,
                        27, 29, 31,  36, 33,
                        34, 35, 36, 38, 40,
                        43, 44, 45, 46,
                        48, 49, 50, 52,
                        53, 57,  58, 59, 60,
                        61, 62, 64, 63, 65,
                        70, 72, 74, 75,78,
                        83, 85, 84, 87, 89,
                        91, 92, 95, 100,
                        101, 103, 104, 106,
                        108, 109, 111, 114,
                        115, 112, 116, 122, 125,
                        126, 127, 130, 124, 130,134,
                        136, 137, 140, 142, 143)
                );
                break;
            case 1:
                preFilledSpots.addAll(Arrays.asList(
                        0, 1, 4, 5, 9,
                        12, 13, 14, 17,
                        18, 21, 22, 25, 26,
                        27, 29, 31, 33,
                        34, 35, 36, 38, 40,
                        43, 46, 49, 53, 59,
                        62, 64, 63, 65,
                        70, 72, 74, 75,78,
                        83, 85, 84, 87, 89,
                        91, 92, 95, 101, 103,
                        108, 109, 111, 114,
                        115, 112,126, 127, 130,
                        124, 130,134,
                        136, 137, 140, 142, 143)
                );
                break;
            case 2:
                preFilledSpots.addAll(Arrays.asList(
                        0, 1, 4, 5, 9,
                        12, 13, 14, 17,
                        18, 21, 22, 25, 26,
                        27, 33, 34, 35, 36,
                        43, 59, 62, 64, 63, 65,
                        70, 72, 74, 75,78,
                        83, 85, 84, 87, 89,
                        91, 92, 95, 101, 103,
                        114, 115, 112,126,
                        124, 136, 137, 140)
                );
                break;

        }

    }

    /* Parcelable Interface methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size);
        dest.writeIntArray(fullBoardWithValues);
        dest.writeSerializable(preFilledSpots);
    }

    @SuppressWarnings("unchecked")
    private SudokuBoard(Parcel source) {
        size = source.readInt();
        fullBoardWithValues = source.createIntArray();
        preFilledSpots = (HashSet) source.readSerializable();
    }

    public static final Parcelable.Creator<SudokuBoard> CREATOR = new Parcelable.Creator<SudokuBoard>() {
        @Override
        public SudokuBoard createFromParcel(Parcel source) {
            return new SudokuBoard(source);
        }

        @Override
        public SudokuBoard[] newArray(int size) {
            return new SudokuBoard[size];
        }
    };
}
