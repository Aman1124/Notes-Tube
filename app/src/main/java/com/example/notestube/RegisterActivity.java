package com.example.notestube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    Button signIn, signUp;
    ImageView ellipse3, ellipse4, welcomeIV, waveHeader;
    TextView welcomeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUp = findViewById(R.id.regSignUpButton);
        signIn = findViewById(R.id.regSignInButton);
        ellipse3 = findViewById(R.id.regEllipse3);
        ellipse4 = findViewById(R.id.regEllipse4);
        welcomeIV = findViewById(R.id.reg_welcome_IV);
        waveHeader = findViewById(R.id.regWave_header);
        welcomeTV = findViewById(R.id.reg_welcomeTV);;

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(waveHeader, "waveHeader_transition");
                pairs[1] = new Pair<View, String>(welcomeIV, "welcomeIV_transition");
                pairs[2] = new Pair<View, String>(ellipse3, "ellipse3_transition");
                pairs[3] = new Pair<View, String>(ellipse4, "ellipse4_transition");
                pairs[4] = new Pair<View, String>(signIn, "signUp_transition");
                pairs[5] = new Pair<View, String>(signUp, "signIn_transition");
                pairs[6] = new Pair<View, String>(welcomeTV, "welcomeTV_transition");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);

                startActivity(intent, activityOptions.toBundle());
            }
        });

        Intent intent = getIntent();
    }
}