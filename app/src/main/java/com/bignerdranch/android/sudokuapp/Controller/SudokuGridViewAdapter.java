package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bignerdranch.android.sudokuapp.R;
import com.bignerdranch.android.sudokuapp.Model.SolutionChecker;
import com.bignerdranch.android.sudokuapp.Model.SudokuBoard;
import com.bignerdranch.android.sudokuapp.Model.WordList;
import com.bignerdranch.android.sudokuapp.Model.WordPair;

public class SudokuGridViewAdapter extends BaseAdapter {
    private static final int LISTENING_MODE_ON = 1;
    private final Context mContext;
    private final SudokuBoard sb;
    private final WordList wl;
    private final int mode;
    private final String[] userEnteredWords;
    private final int listen;
    private final SolutionChecker checker;

    public SudokuGridViewAdapter(Context context, SudokuBoard sb,
                                 WordList wl, int mode, String[] userEnteredWords, int listen,
                                 SolutionChecker checker) {
        this.mContext = context;
        this.sb = sb;
        this.wl = wl;
        this.mode = mode;
        this.userEnteredWords = userEnteredWords;
        this.listen = listen;
        this.checker = checker;
    }

    @Override
    public int getCount() {
        return sb.getBoardLength();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // This method is called whenever the GridView draws itself
    // (Upon creation, and any calls to notifyDataSetChanged())
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.gridview_cell, null);
        TextView textView = (TextView) convertView.findViewById(R.id.cellTextView);
        if (checker.getIncorrectIndices().contains(position)) {
            convertView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.cell_incorrect));
        } else {
            convertView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.cell_unselected));
        }
        SpannableString spanText;
        if (sb.getPreFilledSpots().contains(position)) {
            int value = sb.getFullBoardWithValues()[position];
            WordPair wp = wl.wordPairForValue(value);
            // if mode == 1, then fill the board with words from language B (Japanese)
            //  else fill the board with words from language A (English)
            String word = (mode == 1) ? wp.getLangB() : wp.getLangA();
            if (listen == LISTENING_MODE_ON) {
                spanText = new SpannableString(String.valueOf(value+1));
            } else {
                spanText = new SpannableString(word);
            }
            spanText.setSpan(new StyleSpan(Typeface.BOLD), 0, spanText.length(), 0);
            textView.setTextColor(Color.BLACK);
            textView.setText(spanText);
        } else { // Empty spot
            // If user filled the spot, display word they entered.
            if (userEnteredWords[position] != null) {
                spanText = new SpannableString(userEnteredWords[position]);
            } else {
                spanText = new SpannableString("");
            }
            textView.setTextColor(Color.DKGRAY);
            textView.setText(spanText);
        }

        // Make smaller strings text size in 4x4 and 6x6 grid bigger in tablets to use more of the space
        if (isTablet(mContext) && sb.getSize() == 4 && spanText.length() < 5) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        } else if (isTablet(mContext) && sb.getSize() == 6 && spanText.length() < 4) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        } else {
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,
                    2, 100, 1,
                    TypedValue.COMPLEX_UNIT_SP);
        }

        return convertView;
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
}
