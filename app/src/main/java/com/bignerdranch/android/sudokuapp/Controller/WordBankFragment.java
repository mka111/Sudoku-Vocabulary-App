package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.sudokuapp.Model.FlashCardWords;
import com.bignerdranch.android.sudokuapp.Model.WordPair;
import com.bignerdranch.android.sudokuapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WordBankFragment extends Fragment {
    private RecyclerView mWordBankRecyclerView;
    private WordBankAdapter mWordBankAdapter;

    private MenuItem mDoneButton;

    private List<WordPairWrapper> mWordPairWrappers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FlashCardWords.get(getActivity()).clearWordPairs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word_bank, container, false);
        mWordBankRecyclerView = (RecyclerView) v.findViewById(R.id.wordbank_recycler_view);
        mWordBankRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DBController db = new DBController(getActivity());
        List<WordPair> wps = db.getAllWordPairs();
        mWordPairWrappers = new ArrayList<>();
        for (WordPair wp : wps) {
            WordPairWrapper wpw = new WordPairWrapper(wp);
            wpw.setChecked(false);
            mWordPairWrappers.add(wpw);
        }
        Collections.sort(mWordPairWrappers, new SortByDifficulty());
        mWordBankAdapter = new WordBankAdapter(db.getAllWordPairs());
        mWordBankRecyclerView.setAdapter(mWordBankAdapter);
        getActivity().setTitle("Select Words");
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_button_word_bank:
                int total = 0;
                ArrayList<WordPair> wps = new ArrayList<>();
                for (WordPairWrapper wp : mWordPairWrappers) {
                    if (wp.isChecked()) {
                        wps.add(wp.getWordPair());
                        FlashCardWords.get(getActivity()).addWordPair(wp.getWordPair());
                        total++;
                    }
                }
                Intent intent = FlashcardPagerActivity.newIntent(getActivity(), wps);
                startActivity(intent);
                return true;
            case R.id.clear_button_word_bank:
                clearSelected();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Disable/enable clear/done buttons
        boolean atLeastOneChecked = false;
        for (WordPairWrapper wpw : mWordPairWrappers) {
            if (wpw.isChecked()) {
                atLeastOneChecked = true;
                break;
            }
        }
        if (atLeastOneChecked) {
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        } else {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);
        }
    }

    private class WordPairWrapper {
        private WordPair wp;
        private boolean checked;

        public WordPairWrapper(WordPair wp) {
            this.wp = wp;
        }
        public WordPair getWordPair() {
            return wp;
        }
        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isChecked() {
            return checked;
        }
    }

    private class SortByDifficulty implements Comparator<WordPairWrapper> {
        public int compare(WordPairWrapper a, WordPairWrapper b) {
            return a.getWordPair().getDifficulty() - b.getWordPair().getDifficulty();
        }
    }

    private class WordPairHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mJapaneseTextView;
        private TextView mDifficultyTextView;
        private ImageView mCheckmarkImageView;

        private WordPairWrapper mWordPair;

        public WordPairHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_wordpair, parent, false));
            itemView.setOnClickListener(this);
            mJapaneseTextView = (TextView) itemView.findViewById(R.id.japaneseWord);
            mDifficultyTextView = (TextView) itemView.findViewById(R.id.difficulty);
            mCheckmarkImageView = (ImageView) itemView.findViewById(R.id.checkmark);
        }

        public void bind(WordPairWrapper wp) {
            mWordPair = wp;
            mJapaneseTextView.setText(wp.getWordPair().getLangB());
            String s = String.format("Score: %d", wp.getWordPair().getDifficulty() + 100);
            mDifficultyTextView.setText(s);
            mCheckmarkImageView.setVisibility(wp.isChecked() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (mWordPair.isChecked()) {
                mWordPair.setChecked(false);
                FlashCardWords.get(getActivity()).removeWordPair(mWordPair.getWordPair());
            } else {
                mWordPair.setChecked(true);
            }
            mCheckmarkImageView.setVisibility(mWordPair.isChecked() ? View.VISIBLE : View.GONE);
            getActivity().invalidateOptionsMenu();
        }
    }

    private class WordBankAdapter extends RecyclerView.Adapter<WordPairHolder> {
        private List<WordPairWrapper> mWordPairs;

        public WordBankAdapter(List<WordPair> wordPairs) {
            mWordPairs = mWordPairWrappers;
        }

        @NonNull
        @Override
        public WordPairHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WordPairHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull WordPairHolder wordPairHolder, int i) {
            WordPairWrapper wp = mWordPairs.get(i);
            wordPairHolder.bind(wp);
        }

        @Override
        public int getItemCount() {
            return mWordPairs.size();
        }
    }

    private void clearSelected() {
        for (WordPairWrapper wpw : mWordPairWrappers) {
            wpw.setChecked(false);
        }
        FlashCardWords.get(getActivity()).clearWordPairs();
        mWordBankAdapter.notifyDataSetChanged();
        getActivity().invalidateOptionsMenu();
    }
}
