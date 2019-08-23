package com.bignerdranch.android.sudokuapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.android.sudokuapp.Model.WordPair;
import com.bignerdranch.android.sudokuapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlashcardPagerActivity extends AppCompatActivity {
    private static final String EXTRA_WORD_PAIR = "word_pair";

    private ViewPager mViewPager;
    private List<WordPair> mWordPairs;

    public static Intent newIntent(Context packageContext, ArrayList<WordPair> wps) {
        Intent intent = new Intent(packageContext, FlashcardPagerActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_WORD_PAIR, wps);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_pager);
        setSharedPreferences();

        setTitle("Flashcards");

        mViewPager = (ViewPager) findViewById(R.id.flashcard_view_pager);
        mWordPairs = getIntent().getParcelableArrayListExtra(EXTRA_WORD_PAIR);

        WordPair wordPair = mWordPairs.get(0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                WordPair wp = mWordPairs.get(i);
                return FlashcardFragment.newInstance(wp);
            }

            @Override
            public int getCount() {
                return mWordPairs.size();
            }
        });

        for (int i = 0; i < mWordPairs.size(); i++) {
            if (mWordPairs.get(i).getId() == wordPair.getId()) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flashcard_pager_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.play_sudoku:
                Intent intent = new Intent(this, LanguageActivity.class);
                startActivity(intent);
                break;
            default:
                finish();
        }
        return true;
    }

    private void setSharedPreferences() {
        SharedPreferences pref = getSharedPreferences("GamePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("GameMode", 3);
        editor.apply();
    }
}
