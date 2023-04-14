package com.b2zing.topquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b2zing.topquiz.model.QuestionBank;
import com.b2zing.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    //Instanciate all the element of the layout
    private TextView mWelcomeText;
    private EditText mNameInput;
    private Button mButton;

    //Instanciate a new user
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add the layout to the activity
        setContentView(R.layout.activity_main);

        //Link the layout element to variables
        mWelcomeText = findViewById(R.id.welcome_text);
        mNameInput = findViewById(R.id.input_name_field);
        mButton = findViewById(R.id.button);

        //Create a new user
        mUser = new User();

        //Disable the button by default
        mButton.setEnabled(false);

        //onChange input listener
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("msg", "TextChange");
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e("msg", "afterTextChange");

                //Activate the button when the input is not empty
                mButton.setEnabled(!s.toString().isEmpty());
            }
        });

        //onClick button listener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "Click");

                //Set the name of the user
                mUser.setName(mNameInput.getText().toString());

                //Start GameActivity
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });


    }
}