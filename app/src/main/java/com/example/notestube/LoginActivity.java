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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button signIn, signUp;
    ImageView ellipse3, ellipse4, welcomeIV, waveHeader;
    TextView welcomeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.loginSignUpButton);
        signIn = findViewById(R.id.loginSignInButton);
        ellipse3 = findViewById(R.id.loginEllipse3);
        ellipse4 = findViewById(R.id.loginEllipse4);
        welcomeIV = findViewById(R.id.login_welcome_IV);
        waveHeader = findViewById(R.id.loginWaveHeader);
        welcomeTV = findViewById(R.id.login_welcomeTV);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(waveHeader, "waveHeader_transition");
                pairs[1] = new Pair<View, String>(welcomeIV, "welcomeIV_transition");
                pairs[2] = new Pair<View, String>(ellipse3, "ellipse3_transition");
                pairs[3] = new Pair<View, String>(ellipse4, "ellipse4_transition");
                pairs[4] = new Pair<View, String>(signIn, "signIn_transition");
                pairs[5] = new Pair<View, String>(signUp, "signUp_transition");
                pairs[6] = new Pair<View, String>(welcomeTV, "welcomeTV_transition");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

                startActivity(intent, activityOptions.toBundle());
            }
        });

        Intent intent = getIntent();
    }
}