package com.bignerdranch.android.sudokuapp.Controller;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.sudokuapp.Model.WordPair;
import com.bignerdranch.android.sudokuapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * NOTE: Animation related code is heavily borrowed from:
 * https://github.com/DroidsOnRoids/FlipAnimation-Android
 *
 */
public class FlashcardFragment extends Fragment {
    private static final String WORD_PAIR = "word_pair";
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private View mCardFront;
    private View mCardBack;

    private WordPair mWordPair;
    private TextView mFrontWordTextView;
    private TextView mBackWordTextView;
    private TextView mScoreTextView;
    private ImageView mSpeakerImageView;
    private ImageView mMicrophoneImageView;

    private TextToSpeech mTextToSpeech;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private boolean mIsEnglishWordVisible = false;

    public static FlashcardFragment newInstance(WordPair wp) {
        Bundle args = new Bundle();
        args.putParcelable(WORD_PAIR, wp);

        FlashcardFragment fragment = new FlashcardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordPair = (WordPair) getArguments().getParcelable(WORD_PAIR);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    mTextToSpeech.setLanguage(Locale.JAPANESE);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard, container, false);

        mCardFront = view.findViewById(R.id.card_front);
        mCardBack = view.findViewById(R.id.card_back);

        mFrontWordTextView = (TextView) mCardFront.findViewById(R.id.word_text_view);
        mFrontWordTextView.setText(mWordPair.getLangB());
        mFrontWordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(v);
            }
        });

        mBackWordTextView = (TextView) mCardBack.findViewById(R.id.word_text_view);
        mBackWordTextView.setText(mWordPair.getLangA());
        mBackWordTextView.setVisibility(View.INVISIBLE);
        mBackWordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(v);
            }
        });

        mScoreTextView = (TextView) mCardFront.findViewById(R.id.score_text_view);

        String s = String.format(Locale.CANADA,"Your current score: %d", mWordPair.getDifficulty() + 100);
        mScoreTextView.setText(s);

        mSpeakerImageView = (ImageView) mCardFront.findViewById(R.id.speaker_image_view);
        mSpeakerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mWordPair.getLangB();
                mTextToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        mMicrophoneImageView = (ImageView) mCardFront.findViewById(R.id.microphone_image_view);
        mMicrophoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        loadAnimations();
        changeCameraDistance();

        return view;
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation);
    }

    public void flipCard(View view) {
        if (!mIsEnglishWordVisible) {
            mSetRightOut.setTarget(mCardFront);
            mSetLeftIn.setTarget(mCardBack);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsEnglishWordVisible = true;
            mBackWordTextView.setVisibility(View.VISIBLE);
        } else {
            mSetRightOut.setTarget(mCardBack);
            mSetLeftIn.setTarget(mCardFront);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsEnglishWordVisible = false;
        }
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFront.setCameraDistance(scale);
        mCardBack.setCameraDistance(scale);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CANADA);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    String receivedText;
                    String result = resultList.get(0);
                    if (numbers.get(result) != null) {
                        receivedText = numbers.get(result);
                    } else {
                        receivedText = result;
                    }
                    assert receivedText != null;
                    if (receivedText.equalsIgnoreCase(mWordPair.getLangB())) {
                        Toast.makeText(getActivity(), "Sounds right!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "That didn't sound quite right...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }
}
