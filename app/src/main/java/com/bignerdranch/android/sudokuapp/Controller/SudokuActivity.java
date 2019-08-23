package com.bignerdranch.android.sudokuapp.Controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.sudokuapp.R;
import com.bignerdranch.android.sudokuapp.Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

/**
 * SudokuActivity is the main activity for Sudoku game play
 *
 */
public class SudokuActivity extends AppCompatActivity {
    // Tag for debugging
    private static final String TAG = "SudokuActivity";

    // Tags for onSavedInstanceState
    private static final String WORDS_KEY = "word_list";
    private static final String USER_ENTERED_WORDS_KEY = "user_entered_words";
    private static final String GRID_VIEW_INDEX_KEY = "grid_view_index";
    private static final String SOLUTION_CHECKER_KEY = "solution_checker";
    private static final String CHRONOMETER_KEY = "chronometer";
    private static final String CELLS_FILLED_KEY = "cells_filled";
    private static final String SUDOKU_BOARD_KEY = "sudoku_board";

    private static final int MAX_WORDS = 12;

    /*
     * Model Objects
     */
    // words is a WordList, which is used to represent
    //  the nine pairs of words in language A and language B
    //  (language A = English; language B = Japanese for iteration 1)
    private WordList words;

    // gameMode is provided by the mode activity screen and represents
    //  which words the user can choose to fill the board, as well
    //  as what words will be pre-filled in the Sudoku board.
    // If mode == 0, then the buttons are labelled with 9 random words.
    // If mode == 1, then the buttons are labelled with 9 random words from the top 100 most difficult ones.
    // If mode == 2, then the buttons are labelled with 9 random words from teacher.
    private int gameMode;

    // mode is provided by the language activity screen and represents
    //  which words the user can choose to fill the board, as well
    //  as what words will be pre-filled in the Sudoku board.
    // If mode == 1, then the buttons are labelled with words from
    //  language A (English), and the board is filled with words from
    //  language B (Japanese). Order reversed if mode =/= 1.
    private int mode;

    // SudokuBoard is a representation of a traditional SudokuBoard.
    // See class declaration for details.
    // For values in the SudokuBoard, 0 maps to words[0], 1 to words[1],
    //  ..., 8 to words[8].
    private SudokuBoard board;

    // userEnteredWords is an array to keep track of the words the user
    //  has filled in the grid view
    private String[] userEnteredWords;

    //provided by language activity: listen==1 if listen mode on, 0 if off.
    private int listen;

    // gridViewIndex represents the index of a currently selected cell.
    //  -1 if there is no currently selected cell
    private int gridViewIndex;

    // cellFilled is used to keep track of the number of cells filled in the board
    private int cellsFilled;

    //SolutionChecker is a class to hold user answers and check them afterwards
    private SolutionChecker solutionChecker;

    // controller for the work with database
    private DBController mDBController;

    // difficultyTracker tracks the difficulty of the word for a given user
    private DifficultyTracker difficultyTracker;

    // Number of words on the board
    private int numberOfWords;

    //To keep track of pre-filled cells on the board based on the difficulty level chosen by the user
    private int level;

    //provided by language activity: pronunciation == 1 if pronunciation mode on, 0 if off.
    private int pronunciation;

    // String for the result of text recognizer
    private String txtSpeechInput;

    // REQUIRED_CODE_SPEECH_INPUT
    private final int REQ_CODE_SPEECH_INPUT = 100;

    // language locale for SpeechToText
    private Locale language;

    // Boolean to keep track of delete/edit state
    private boolean inDeleteState = false;

    /*
     * View Objects
     */
    // mGridView is used to represent the Sudoku board
    private GridView mGridView;

    // mSudokuGridViewAdapter is used to manage the data for mGridView
    //  and to provide the view for its cells
    private SudokuGridViewAdapter mSudokuGridViewAdapter;

    // mButtonArray is used help manage mButtons 0-11
    private List<Button> mButtonArray;

    // mSimpleChronometer is used to display the time elapsed as
    //  the user plays the game
    private Chronometer mSimpleChronometer;

    // prevSelectedCell is used to hold a reference to a previously
    //  selected cell in the grid view (if any)
    private View prevSelectedCell;

    // text to speech for listener mode to have the words read out
    private TextToSpeech mTextToSpeech;

    /*
     * Activity methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        initializeModels(savedInstanceState);
        initializeViews();
        configureBoard();
        startTimer(savedInstanceState);
    }

    @Override
    public void onPause() {
        updateDifficulty();
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        updateDifficulty();
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(WORDS_KEY, words);
        outState.putStringArray(USER_ENTERED_WORDS_KEY, userEnteredWords);
        outState.putInt(GRID_VIEW_INDEX_KEY, gridViewIndex);
        outState.putParcelable(SOLUTION_CHECKER_KEY, solutionChecker);
        outState.putLong(CHRONOMETER_KEY, mSimpleChronometer.getBase());
        outState.putInt(CELLS_FILLED_KEY, cellsFilled);
        outState.putParcelable(SUDOKU_BOARD_KEY, board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sudoku_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (solutionChecker.getIncorrectIndices().size() == 0) {
            menu.getItem(0).setEnabled(false);
        } else {
            menu.getItem(0).setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                clearAllIncorrectAnswers();
                break;
            default: // back button pressed
                finish();
        }
        return true;
    }

    /*
     * Private methods
     */
    // All code to properly initialize the models objects are placed here
    private  void initializeModels(Bundle savedInstanceState) {
        // Get mode from LanguageActivity
        Intent intent = getIntent();
        SharedPreferences pref = getSharedPreferences("GamePref", Context.MODE_PRIVATE);
        numberOfWords = intent.getIntExtra(LanguageActivity.SIZE, 9);
        gameMode = pref.getInt("GameMode", 1);
        mode = intent.getIntExtra(LanguageActivity.MODE, 1);
        listen = intent.getIntExtra(LanguageActivity.LISTEN,0);
        level = intent.getIntExtra(LanguageActivity.LEVEL,0);
        pronunciation = intent.getIntExtra(LanguageActivity.PRONUNCIATION,0);
        mDBController = new DBController(this);
        language = (mode == 1) ? Locale.CANADA : Locale.JAPANESE;
        txtSpeechInput = "";

        if (savedInstanceState != null) {
            words = savedInstanceState.getParcelable(WORDS_KEY);
            userEnteredWords = savedInstanceState.getStringArray(USER_ENTERED_WORDS_KEY);
            gridViewIndex = savedInstanceState.getInt(GRID_VIEW_INDEX_KEY, -1);
            solutionChecker = savedInstanceState.getParcelable(SOLUTION_CHECKER_KEY);
            cellsFilled = savedInstanceState.getInt(CELLS_FILLED_KEY);
            board = savedInstanceState.getParcelable(SUDOKU_BOARD_KEY);
        } else {
            switch (gameMode) {
                case 1 :
                    words = new WordList(mDBController.getDataForDifficultyMode(numberOfWords), numberOfWords);
                    break;
                case 2 :
                    words = new WordList(mDBController.getDataForTeacherMode(numberOfWords), numberOfWords);
                    break;
                case 3: // Flashcard words
                    words = FlashCardWords.get(this).getWordListForSize(numberOfWords);
                    break;
                default:
                    words = new WordList(mDBController.getDataForNormalMode(numberOfWords), numberOfWords);
            }
            board = new SudokuBoard(numberOfWords, level);
            userEnteredWords = new String[board.getBoardLength()];
            gridViewIndex = -1;
            solutionChecker = new SolutionChecker(board, words, numberOfWords, board.getBoardLength());
            cellsFilled = board.getPreFilledSpots().size();
        }

        difficultyTracker = new DifficultyTracker(Objects.requireNonNull(board), words);
    }
    // All code to properly initialize the view objects are placed here.
    private void initializeViews() {
        mGridView = (GridView) findViewById(R.id.gridview);
        layoutButtonsForSize(numberOfWords);
        labelButtonsAndSetOnClickListener();
        // initiate a chronometer
        mSimpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        prevSelectedCell = null;
        //setting language
        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    if (mode == 1) {
                        mTextToSpeech.setLanguage(Locale.JAPANESE);
                    } else {
                        mTextToSpeech.setLanguage(Locale.ENGLISH);
                    }
            }
        });
    }

    private void configureBoard() {
        mGridView.setNumColumns(numberOfWords);
        mSudokuGridViewAdapter = new SudokuGridViewAdapter(this, board,
                words, mode, userEnteredWords, listen, solutionChecker);
        mGridView.setAdapter(mSudokuGridViewAdapter);
        // All code to handle cell selection goes here...
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prevSelectedCell != null) {
                    prevSelectedCell.setBackground(
                            ContextCompat.getDrawable(SudokuActivity.this,
                                    R.drawable.cell_unselected));
                }
                // Ensure that all incorrectly filled cells are still highlighted
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    for (Integer i : solutionChecker.getIncorrectIndices()) {
                        if (parent.getChildAt(i) != null) {
                            parent.getChildAt(i).setBackground(ContextCompat.getDrawable(SudokuActivity.this,
                                    R.drawable.cell_incorrect));
                        }
                    }
                }
                if (board.getPreFilledSpots().contains(position)) {
                    int value = board.getFullBoardWithValues()[position];
                    WordPair wp = words.wordPairForValue(value);
                    if (listen == 1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        String word = (mode == 1) ? wp.getLangB() : wp.getLangA();
                        mTextToSpeech.speak(word,TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        String word = (mode == 1) ? wp.getLangA() : wp.getLangB();
                        Toast.makeText(SudokuActivity.this, word, Toast.LENGTH_SHORT).show();
                    }
                    setGridViewIndexAndPrevSelectedCell(-1, null);
                } else { // User selected a non-pre-filled cell
                    setGridViewIndexAndPrevSelectedCell(position, view);
                    if (userEnteredWords[position] == null) {
                        inDeleteState = false;
                        view.setBackground(ContextCompat.getDrawable(SudokuActivity.this,
                                R.drawable.cell_selected_insert));
                        if (pronunciation == 1) {
                            promptSpeechInput();
                        }
                    } else {
                        inDeleteState = true;
                        view.setBackground(ContextCompat.getDrawable(SudokuActivity.this,
                                R.drawable.cell_selected_delete));
                        if (pronunciation == 1) {
                            promptSpeechInput();
                        }
                    }
                }
            }
        });
    }

    private void layoutButtonsForSize(int size) {
        // Button's 0-11 represent the 9 choices the user can select
        //  for a cell in the grid view. The buttons are labelled
        //  appropriately via the labelButtonsAndSetOnClickListener() method.
        Button mButton0 = (Button) findViewById(R.id.button0);
        Button mButton1 = (Button) findViewById(R.id.button1);
        Button mButton2 = (Button) findViewById(R.id.button2);
        Button mButton3 = (Button) findViewById(R.id.button3);
        Button mButton4 = (Button) findViewById(R.id.button4);
        Button mButton5 = (Button) findViewById(R.id.button5);
        Button mButton6 = (Button) findViewById(R.id.button6);
        Button mButton7 = (Button) findViewById(R.id.button7);
        Button mButton8 = (Button) findViewById(R.id.button8);
        Button mButton9 = (Button) findViewById(R.id.button9);
        Button mButton10 = (Button) findViewById(R.id.button10);
        Button mButton11 = (Button) findViewById(R.id.button11);
        Set<Button> buttonSet = new HashSet<>();
        Set<Button> allButtons = new HashSet<>(Arrays.asList(
                mButton0, mButton1, mButton2, mButton3, mButton4,
                mButton5, mButton6, mButton7, mButton8, mButton9,
                mButton10, mButton11));
        List<Button> buttonsToRemove = new ArrayList<>();
        int orientation = getResources().getConfiguration().orientation;
        if (!isTablet(this)) { // Layouts for phone
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                switch (size) {
                    case 4: // 4x4 grid
                        buttonSet.addAll(Arrays.asList(
                                mButton6, mButton7, mButton9, mButton10
                        ));
                        break;
                    case 6:
                        buttonSet.addAll(Arrays.asList(
                                mButton6, mButton7, mButton8,
                                mButton9, mButton10, mButton11
                        ));
                        break;
                    case 9:
                        buttonSet.addAll(Arrays.asList(
                                mButton3, mButton4, mButton5,
                                mButton6, mButton7, mButton8,
                                mButton9, mButton10, mButton11
                        ));
                        break;
                    default:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2, mButton3,
                                mButton4, mButton5, mButton6, mButton7,
                                mButton8, mButton9, mButton10, mButton11
                        ));
                }
            } else { // Layouts for phone in landscape
                switch (size) {
                    case 4: // 4x4 grid
                        buttonSet.addAll(Arrays.asList(
                                mButton6, mButton7, mButton8, mButton9
                        ));
                        break;
                    case 6:
                        buttonSet.addAll(Arrays.asList(
                               mButton6, mButton7, mButton8, mButton9, mButton10, mButton11
                        ));
                        break;
                    case 9:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2, mButton3,
                                mButton6, mButton7, mButton8, mButton9,
                                mButton10
                        ));
                        break;
                    default:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2, mButton3,
                                mButton4, mButton5, mButton6, mButton7,
                                mButton8, mButton9, mButton10, mButton11
                        ));
                }
            }
        } else { // Layouts for tablets
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                switch (size) {
                    case 4: // 4x4 grid
                        buttonSet.addAll(Arrays.asList(
                                mButton4, mButton5,
                                mButton8, mButton9
                        ));
                        break;
                    case 6:
                        buttonSet.addAll(Arrays.asList(
                                mButton4, mButton5, mButton6,
                                mButton8, mButton9, mButton10
                        ));
                        break;
                    case 9:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2,
                                mButton4, mButton5, mButton6,
                                mButton8, mButton9, mButton10
                        ));
                        break;
                    default:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2, mButton3,
                                mButton4, mButton5, mButton6, mButton7,
                                mButton8, mButton9, mButton10, mButton11
                        ));
                }
            } else { // Layouts for tablets in landscape
                switch (size) {
                    case 4: // 4x4 grid
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton3, mButton4
                        ));
                        break;
                    case 6:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2,
                                mButton3, mButton4, mButton5
                        ));
                        break;
                    case 9:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2,
                                mButton3, mButton4, mButton5,
                                mButton6, mButton7, mButton8
                        ));
                        break;
                    default:
                        buttonSet.addAll(Arrays.asList(
                                mButton0, mButton1, mButton2, mButton3,
                                mButton4, mButton5, mButton6, mButton7,
                                mButton8, mButton9, mButton10, mButton11
                        ));
                }
            }
        }
        for (Button button : allButtons) {
            if (!buttonSet.contains(button)) {
                buttonsToRemove.add(button);
            }
        }
        for (Button button : buttonsToRemove) {
            button.setVisibility(View.GONE);
        }
        mButtonArray = new ArrayList<>();
        for (Button button : buttonSet) {
            mButtonArray.add(button);
            //Turn off buttons in pronunciation mode
            if (pronunciation == 1) {
                button.setEnabled(false);
            }
        }
    }

    private void labelButtonsAndSetOnClickListener() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        for (int i = 0; i < numberOfWords; i++) {
            // Get random word pair in range 0..<numberOfWords
            int randomValue = values.remove(0);
            WordPair wp = words.wordPairForValue(randomValue);
            // If mode == 1, label buttons with LangA (English), else LangB (Japanese)
            String word = (mode == 1) ? wp.getLangA() : wp.getLangB();
            mButtonArray.get(i).setText(word);
            autoSizeTextView(((TextView)mButtonArray.get(i)));
            // Code to handle clicks for buttons 0 through numberOfWords-1
            mButtonArray.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn = (Button) v;
                    String btnString = btn.getText().toString();
                    if (gridViewIndex != -1 && !inDeleteState) {
                        userEnteredWords[gridViewIndex] = btnString;
                        cellsFilled = cellsFilled + 1;
                        solutionChecker.addUserAnswer(gridViewIndex, btnString);
                        int correctValue = board.getFullBoardWithValues()[gridViewIndex];
                        int wordValue = words.valueForWord(btnString);
                        if (correctValue != wordValue)  {
                            showToast("Sorry, but you made a mistake :(", RED, Toast.LENGTH_SHORT);
                            invalidateOptionsMenu();
                        }
                        difficultyTracker.countDifficulty(btnString, gridViewIndex);
                        setGridViewIndexAndPrevSelectedCell(-1, null);
                        mSudokuGridViewAdapter.notifyDataSetChanged();
                        inDeleteState = false;
                    }
                    if(gridViewIndex != -1 && inDeleteState) {
                        // Replace word on board with new one
                        solutionChecker.deleteUserAnswer(gridViewIndex);
                        userEnteredWords[gridViewIndex] = btnString;
                        solutionChecker.addUserAnswer(gridViewIndex, btnString);
                        int correctValue = board.getFullBoardWithValues()[gridViewIndex];
                        int wordValue = words.valueForWord(btnString);
                        if (correctValue != wordValue)  {
                            showToast("Sorry, but you made a mistake :(", RED, Toast.LENGTH_SHORT);
                            invalidateOptionsMenu();
                        }
                        setGridViewIndexAndPrevSelectedCell(-1, null);
                        mSudokuGridViewAdapter.notifyDataSetChanged();
                        inDeleteState = false;
                    }
                    if (cellsFilled == board.getBoardLength()) {
                        finalCheck();
                    }
                    if (solutionChecker.getIncorrectIndices().size() == 0) {
                        invalidateOptionsMenu(); // disable clear button
                    }
                }
            });
        }
    }

    private void finalCheck() {
        if (solutionChecker.checkAnswers()){
            showToast("Wonderful job! :)", GREEN, Toast.LENGTH_LONG);

            updateDifficulty();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Go to main screen
                    Intent intent = new Intent(SudokuActivity.this, MainActivity.class);
                    // Pop off all other activities from the stack
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            showToast("Sorry, but you have some mistakes :(", RED, Toast.LENGTH_LONG);
        }
    }

    private void showToast(String toastText, @ColorInt int color, int lengthLong) {
        Toast toast = Toast.makeText(SudokuActivity.this, toastText, lengthLong);
        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //Gets the TextView from the Toast so it can be edited
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(WHITE);

        toast.show();
    }

    private void startTimer(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mSimpleChronometer.setBase(savedInstanceState.getLong(CHRONOMETER_KEY));
        mSimpleChronometer.start(); // start a chronometer
    }

    private void setGridViewIndexAndPrevSelectedCell(int index, View view) {
        gridViewIndex = index;
        prevSelectedCell = view;
    }

    private void updateDifficulty() {
        for (int i = 0; i < numberOfWords; i++) {
            // Get button string at index i to get correct word pair
            String btnString = mButtonArray.get(i).getText().toString();
            int wordValue = words.valueForWord(btnString);
            WordPair wordPair = words.wordPairForValue(wordValue);
            int id = wordPair.getId();
            int difficulty = wordPair.getDifficulty();
            mDBController.updateDifficulty(id, difficulty);
        }
    }

    private void autoSizeTextView(TextView textView) {
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,
                8, 14, 1,
                TypedValue.COMPLEX_UNIT_SP);
    }

    // The tutorial (https://github.com/ashokslsk/SpeakToMe) was used to create SpeechToText module

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, String> numbers = new HashMap<>();
        numbers.put("0", "ゼロ");
        numbers.put("1", "一");
        numbers.put("2", "二");
        numbers.put("3", "三");
        numbers.put("4", "四");
        numbers.put("5", "五");
        numbers.put("6", "六");
        numbers.put("7", "七");
        numbers.put("8", "八");
        numbers.put("9", "九");

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> resultList = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String result = resultList.get(0);
                    if (numbers.get(result) != null) {
                        txtSpeechInput = numbers.get(result);
                    } else {
                        txtSpeechInput = result;
                    }
                    if (gridViewIndex != -1) {
                        userEnteredWords[gridViewIndex] = txtSpeechInput;
                        if (!inDeleteState) {
                            cellsFilled = cellsFilled + 1;
                        } else {
                            solutionChecker.deleteUserAnswer(gridViewIndex);
                        }
                        solutionChecker.addUserAnswer(gridViewIndex, txtSpeechInput);
                        int correctValue = board.getFullBoardWithValues()[gridViewIndex];
                        int wordValue = words.valueForWord(txtSpeechInput);
                        if (correctValue != wordValue)  {
                            showToast("Sorry, but you made a mistake :(", RED, Toast.LENGTH_SHORT);
                            invalidateOptionsMenu(); // to enable clear button
                        } else {
                            difficultyTracker.countDifficulty(txtSpeechInput, gridViewIndex);
                        }
                        setGridViewIndexAndPrevSelectedCell(-1, null);
                        mSudokuGridViewAdapter.notifyDataSetChanged();
                        inDeleteState = false;
                    }
                    if (cellsFilled == board.getBoardLength()) {
                        finalCheck();
                    }
                }
                break;
            }

        }
    }

    // Code from:
    // https://stackoverflow.com/questions/16784101/how-to-find-tablet-or-phone-in-android-programmatically
    private boolean isTablet(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        boolean xlarge = ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private void clearAllIncorrectAnswers() {
        Set<Integer> indices = new HashSet<>(solutionChecker.getIncorrectIndices());
        for (Integer i : indices) {
            // Remove from userEnteredWords
            userEnteredWords[i] = null;
            // Remove from solutionChecker
            solutionChecker.deleteUserAnswer(i);
            // Decrement cell's filled
            cellsFilled--;
        }
        // Call notifyDataSetChanged
        mSudokuGridViewAdapter.notifyDataSetChanged();
        invalidateOptionsMenu();
    }
}
