package com.bignerdranch.android.sudokuapp.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bignerdranch.android.sudokuapp.R;

public class WordBankActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_word_bank_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_button_word_bank:
                return false;
            case R.id.clear_button_word_bank:
                return false;
            default:
                finish();
                return true;
        }
    }

    @Override
    protected Fragment createFragment() {
        return new WordBankFragment();
    }
}
