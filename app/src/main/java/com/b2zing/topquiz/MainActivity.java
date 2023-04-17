package com.b2zing.topquiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b2zing.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GAME_ACTIVITY = 10;

    //Instanciate all the element of the layout
    private TextView mWelcomeText;
    private EditText mNameInput;
    private Button mButton;
    private TextView mBestScore;
    private TextView mBestName;

    //Instanciate a new user
    private User mUser;


    private static final String USER_INFO_NAME = "USER_INFO_NAME";
    private static final String USER_INFO_SCORE = "USER_INFO_SCORE";
    private static final String USER_INFO = "USER_INFO";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add the layout to the activity
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);

        //Link the layout element to variables
        mWelcomeText = findViewById(R.id.welcome_text);
        mNameInput = findViewById(R.id.input_name_field);
        mButton = findViewById(R.id.button);
        mBestScore = findViewById(R.id.bestScore);
        mBestName = findViewById(R.id.bestName);

        //Create a new user
        mUser = new User();

        //Disable the button by default
        mButton.setEnabled(false);

        if(preferences.getString(USER_INFO_NAME, null) != null) {
            mBestName.setText(preferences.getString(USER_INFO_NAME, null));
        }


        //TO-DO => Replace 0 by -1
        if(preferences.getInt(USER_INFO_SCORE, -1) != -1) {
            mBestScore.setText(Integer.toString(preferences.getInt(USER_INFO_SCORE, -1)));
        }

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

        ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback() {
                    @Override
                    public void onActivityResult(Object result) {
                        ActivityResult res = (ActivityResult) result;

                        if (res.getResultCode() == RESULT_OK && res.getData() != null) {
                            // Fetch the score from the Intent
                            int score = res.getData().getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

                            int prevScore = preferences.getInt(USER_INFO_SCORE, -1);

                            if(prevScore < score) {
                                preferences.edit().putInt(USER_INFO_SCORE, score).apply();
                            }

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                            Log.e("msg", "Score : " + score);
                        }
                    }
                });

        //onClick button listener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "Click");

                //Set the name of the user
                mUser.setName(mNameInput.getText().toString());

                if(preferences.getString(USER_INFO_NAME, null) == null) {
                    preferences.edit().putString(USER_INFO_NAME, mUser.getName()).apply();
                }

                //Start GameActivity
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                mGetContent.launch(gameActivityIntent);
            }
        });


    }
}