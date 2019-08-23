package com.bignerdranch.android.sudokuapp.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolutionChecker implements Parcelable {
    private HashMap<Integer, Integer> userBoard;
    private WordList words;
    private SudokuBoard board;
    private HashSet<Integer> incorrectIndices;

    // Number of words on the board
    private int numberOfWords;
    // Number of positions on the board
    private int numberOfPositions;

    @SuppressLint("UseSparseArrays")
    public SolutionChecker(SudokuBoard board, WordList words, int numberOfWords, int numberOfPositions) {
        this.numberOfWords = numberOfWords;
        this.numberOfPositions = numberOfPositions;
        this.words = words;
        this.board = board;
        int[] fullBoardWithValues = board.getFullBoardWithValues();
        Set<Integer> filledSpots = board.getPreFilledSpots();
        userBoard = new HashMap<>();
        for (Integer filledSpot : filledSpots) {
            userBoard.put(filledSpot, fullBoardWithValues[filledSpot]);
        }
        incorrectIndices = new HashSet<>();
    }

    public void addUserAnswer(Integer wordPosition, String word)
    {
        int value = words.valueForWord(word);
        if (board.getFullBoardWithValues()[wordPosition] != value) {
            incorrectIndices.add(wordPosition);
        } else if (board.getFullBoardWithValues()[wordPosition] == value) {
            incorrectIndices.remove(wordPosition);
        }
        userBoard.put(wordPosition, value);
    }

    public void deleteUserAnswer(Integer wordPosition) {
        incorrectIndices.remove(wordPosition);
        userBoard.remove(wordPosition);
    }

    public boolean checkAnswers()
    {
        return (incorrectIndices.size() == 0) && checkRows() && checkColumns() && checkSquares();
    }

    public Set<Integer> getIncorrectIndices() {
        return incorrectIndices;
    }

    private boolean checkSquares() {
        ArrayList<List<Integer>> squareCollection = new ArrayList<>();
        switch (this.numberOfWords) {
            case 4:
                fillSquareCollectionForFourWords(squareCollection);
                break;
            case 6:
                fillSquareCollectionForSixWords(squareCollection);
                break;
            case 9:
                fillSquareCollectionForNineWords(squareCollection);
                break;
            case 12:
                fillSquareCollectionForTwelveWords(squareCollection);
        }


        for (List<Integer> square : squareCollection) {
            Set<Integer> squareChecker = new HashSet<>();
            for (Integer position : square) {
                Integer value = userBoard.get(position);
                if (value == null) {
                    return false;
                }
                if (!squareChecker.add(value)) {
                    return false;
                }
            }

        }
        return true;
    }

    private void fillSquareCollectionForFourWords(ArrayList<List<Integer>> squareCollection) {
        List<Integer> square1 = Arrays.asList(0, 1, 4, 5);
        List<Integer> square2 = Arrays.asList(2, 3, 6, 7);

        List<Integer> square3 = Arrays.asList(8, 9, 12, 13);
        List<Integer> square4 = Arrays.asList(10, 11, 14, 15);

        squareCollection.add(square1);
        squareCollection.add(square2);
        squareCollection.add(square3);
        squareCollection.add(square4);
    }

    private void fillSquareCollectionForSixWords(ArrayList<List<Integer>> squareCollection) {
        List<Integer> square1 = Arrays.asList(0, 1, 2, 6, 7, 8);
        List<Integer> square2 = Arrays.asList(3, 4, 5, 9, 10, 11);

        List<Integer> square3 = Arrays.asList(12, 13, 14, 18, 19, 20);
        List<Integer> square4 = Arrays.asList(15, 16, 17, 21, 22, 23);

        List<Integer> square5 = Arrays.asList(24, 25, 26, 30, 31, 32);
        List<Integer> square6 = Arrays.asList(27, 28, 29, 33, 34, 35);

        squareCollection.add(square1);
        squareCollection.add(square2);
        squareCollection.add(square3);
        squareCollection.add(square4);
        squareCollection.add(square5);
        squareCollection.add(square6);
    }

    private void fillSquareCollectionForNineWords(ArrayList<List<Integer>> squareCollection) {
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
    }

    private void fillSquareCollectionForTwelveWords(ArrayList<List<Integer>> squareCollection) {
        List<Integer> square1 = Arrays.asList(0, 1, 2, 3, 12, 13, 14, 15, 24, 25, 26, 27);
        List<Integer> square2 = Arrays.asList(4, 5, 6, 7, 16, 17, 18, 19, 28, 29, 30, 31);
        List<Integer> square3 = Arrays.asList(8, 9, 10, 11, 20, 21, 22, 23, 32, 33, 34, 35);

        List<Integer> square4 = Arrays.asList(36, 37, 38, 39, 48, 49, 50, 51, 60, 61, 62, 63);
        List<Integer> square5 = Arrays.asList(40, 41, 42, 43, 52, 53, 54, 55, 64, 65, 66, 67);
        List<Integer> square6 = Arrays.asList(44, 45, 46, 47, 56, 57, 58, 59, 68, 69, 70, 71);

        List<Integer> square7 = Arrays.asList(72, 73, 74, 75, 84, 85, 86, 87, 96, 97, 98, 99);
        List<Integer> square8 = Arrays.asList(76, 77, 78, 79, 88, 89, 90, 91,100, 101, 102, 103);
        List<Integer> square9 = Arrays.asList(80, 81, 82, 83, 92, 93, 94, 95, 104, 105, 106, 107);

        List<Integer> square10 = Arrays.asList(108, 109, 110, 111, 120, 121, 122, 123, 132, 133, 134, 135);
        List<Integer> square11 = Arrays.asList(112, 113, 114, 115, 124, 125, 126, 127, 136, 137, 138, 139);
        List<Integer> square12 = Arrays.asList(116, 117, 118, 119, 128, 129, 130, 131, 140, 141, 142, 143);

        squareCollection.add(square1);
        squareCollection.add(square2);
        squareCollection.add(square3);
        squareCollection.add(square4);
        squareCollection.add(square5);
        squareCollection.add(square6);
        squareCollection.add(square7);
        squareCollection.add(square8);
        squareCollection.add(square9);
        squareCollection.add(square10);
        squareCollection.add(square11);
        squareCollection.add(square12);
    }

    private boolean checkColumns() {
        for (int i = 0; i < numberOfWords; i++) {
            Set<Integer> columnChecker = new HashSet<>();
            for (int j = 0; j < numberOfPositions; j += numberOfWords) {
                Integer value = userBoard.get(i + j);
                if (value == null) {
                    return false;
                }
                if (!columnChecker.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRows() {
        for (int i = 0; i < numberOfWords; i++) {
            Set<Integer> rowChecker = new HashSet<>();
            for (int j = 0; j < numberOfWords; j++) {
                Integer value = userBoard.get(i* numberOfWords + j);
                if (value == null) {
                    return false;
                }
                if (!rowChecker.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* Parcelable Interface Methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(userBoard);
        dest.writeParcelable(words, 0);
        dest.writeSerializable(incorrectIndices);
    }

    @SuppressWarnings("unchecked")
    private SolutionChecker(Parcel source) {
        userBoard = (HashMap) source.readSerializable();
        words = source.readParcelable(WordList.class.getClassLoader());
        incorrectIndices = (HashSet) source.readSerializable();
    }

    public static final Parcelable.Creator<SolutionChecker> CREATOR = new Parcelable.Creator<SolutionChecker>() {
        @Override
        public SolutionChecker createFromParcel(Parcel source) {
            return new SolutionChecker(source);
        }

        @Override
        public SolutionChecker[] newArray(int size) {
            return new SolutionChecker[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof SolutionChecker))
            return false;
        if (obj == this)
            return true;
        return userBoard.equals(((SolutionChecker) obj).userBoard);
    }
}
