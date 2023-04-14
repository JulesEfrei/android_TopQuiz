package com.b2zing.topquiz.model;

import java.util.List;

public class Question {

    private final String mQuestion;
    private final List<String> mAnswerList;
    private final int mAnswerIndex;

    //Create a question with the question, the answers and the index of the right answer
    public Question(String question, List<String> answerList, int answerIndex) {
        mQuestion = question;
        mAnswerList = answerList;
        mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getAnswerList() {
        return mAnswerList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
