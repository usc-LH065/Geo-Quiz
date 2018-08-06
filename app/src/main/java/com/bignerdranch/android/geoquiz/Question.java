package com.bignerdranch.android.geoquiz;

public class Question {
    private String mText = "";
    private boolean isTrue = true;
    public Question(String txt, boolean answer){ mText = txt;  isTrue = answer; }
    public boolean isAnswerTrue(){
        return isTrue;
    }
    public String getText(){
        return mText;
    }
}
