package com.b2zing.topquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.b2zing.topquiz.model.Question;
import com.b2zing.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int INITIAL_QUESTION_COUNT = 4;

    //Instanciate the bank of questions
    private QuestionBank mQuestionBank;
    //Instanciate remain questions counter
    private int mRemainingQuestionCount;

    //Intanciate the layout elements
    private TextView mTextViewQuestion;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Link the layout element to the variable
        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);

        //We create attach the onClick method listener to all the button
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mRemainingQuestionCount = INITIAL_QUESTION_COUNT;
        mQuestionBank = generateQuestionBank();

        displayQuestion(mQuestionBank.getCurrentQuestion());
    }

    private void displayQuestion(final Question question) {
        //Set the text for the question text view and the four buttons
        mTextViewQuestion.setText(question.getQuestion());
        mAnswerButton1.setText(question.getAnswerList().get(0));
        mAnswerButton2.setText(question.getAnswerList().get(1));
        mAnswerButton3.setText(question.getAnswerList().get(2));
        mAnswerButton4.setText(question.getAnswerList().get(3));
    }

    private QuestionBank generateQuestionBank() {
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        Question question4 = new Question(
                "Who did the Mona Lisa paint?",
                Arrays.asList(
                        "Michelangelo",
                        "Leonardo Da Vinci",
                        "Raphael",
                        "Carravagio"
                ),
                1
        );

        Question question5 = new Question(
                "What is the country top-level domain of Belgium?",
                Arrays.asList(
                        ".bg",
                        ".bm",
                        ".bl",
                        ".be"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5));
    }

    @Override
    public void onClick(View view) {
        int index;

        if(view == mAnswerButton1) {
            index = 0;
        } else if(view == mAnswerButton2) {
            index = 1;
        } else if(view == mAnswerButton3) {
            index = 2;
        } else if(view == mAnswerButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        if(index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Log.e("msg", "Answer correct!");

            //Show toast
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
        } else {
            Log.e("msg", "Answer Incorrect!");

            //Show toast
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
        }

        mRemainingQuestionCount--;

        if (mRemainingQuestionCount > 0) {
            Question mCurrentQuestion = mQuestionBank.getNextQuestion();
            displayQuestion(mCurrentQuestion);
        } else {
            // No question left, end the game
            Log.v("msg", "End of the game");
        }

    }
}