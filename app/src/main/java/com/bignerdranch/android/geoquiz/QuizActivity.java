package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final Integer REQUSET_CODE_CHEAT = 0;


    private Question[] mQuestionBank;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Integer mCurrentIndex = 0;
    private boolean mIsCheater;
    private TextView mQuestionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt("INDEX");
            Toast.makeText(this,"start New activity " +mCurrentIndex, Toast.LENGTH_SHORT).show();
        }
        mQuestionBank = new Question[5];
        mQuestionBank[0] = new Question("Paraguay is in Africa", false);
        mQuestionBank[1] = new Question("World War II was a global war that lasted from 1939 to 1945 ", true);
        mQuestionBank[2] = new Question("The 2016 Summer Olympics was held in South Korea", false);
        mQuestionBank[3] = new Question("France  is a big winner of the World Cup", true);
        mQuestionBank[4] = new Question("Columbs was the first person to discover the America continent",  false);

//        updateQuestion();

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;

                updateQuestion();
            }
        });
        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i,REQUSET_CODE_CHEAT);
            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);

        updateQuestion();
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getText());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",mCurrentIndex);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUSET_CODE_CHEAT) {
            if(data==null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            Toast.makeText(this, mIsCheater + "", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if( mIsCheater){
            messageResId = R.string.judgment_toast;
        } else {
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("INDEX",mCurrentIndex);
        Toast.makeText(this,"save instance", Toast.LENGTH_SHORT).show();
    }
}

