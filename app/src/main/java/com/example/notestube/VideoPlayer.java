package com.example.notestube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends YouTubeBaseActivity {

    YouTubePlayerView mYoutubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    TextView titleTV, timeTV, channelTV, descriptionTV;
    ImageView dropDown;
    String title, time, channel, description = "aaaaa\naaaaaaa\naaaaaaaa\naaaaaaaa";
    Boolean descExpanded = false;

    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mYoutubePlayerView=findViewById(R.id.youtubeplay);
        titleTV = findViewById(R.id.videoPlayerTitle);
        timeTV = findViewById(R.id.videoPlayerTime);
        channelTV = findViewById(R.id.videoPlayerChannel);
        descriptionTV = findViewById(R.id.videoPlayerDescription);
        dropDown = findViewById(R.id.vpDescStatImg);

        params = (LinearLayout.LayoutParams) descriptionTV.getLayoutParams();

        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo("1_iUFT3nWHk");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!descExpanded){
                   params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                   descriptionTV.setLayoutParams(params);
                   dropDown.setImageResource(R.drawable.ic_arrow_up);
                   descExpanded = true;
                } else{
                  params.height = 0;
                  descriptionTV.setLayoutParams(params);
                  dropDown.setImageResource(R.drawable.ic_arrow_down);
                  descExpanded = false;
                }
            }
        });

        mYoutubePlayerView.initialize("AIzaSyA0epWMVtHlvhZF2WsApIUFr_D0dFU_IY4",onInitializedListener);
        title = "TITLE";
        time = "TIME";
        channel = "CHANNEL";
        initializeTextView();
    }

    private void initializeTextView(){
        titleTV.setText(title);
        timeTV.setText(time);
        channelTV.setText(channel);
        descriptionTV.setText(description);
    }
}