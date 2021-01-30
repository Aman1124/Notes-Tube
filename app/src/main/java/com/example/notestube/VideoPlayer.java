package com.example.notestube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Locale;

public class VideoPlayer extends YouTubeBaseActivity {

    YouTubePlayerView mYoutubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    TextView titleTV, timeTV, channelTV, descriptionTV;
    ImageView dropDown;

    String title, time, channel, description,videoId;
    EditText editText;
    Boolean descExpanded = false;
    Boolean editNote = false;

    String note = "";
    SQLiteDatabase database;

    LinearLayout.LayoutParams params;
    ConstraintLayout layout, mainLayout;

    @Override
    public void onBackPressed() {
        if(editNote){
            editNote = false;
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            layout.setVisibility(View.INVISIBLE);
            saveNote();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent=getIntent();
        VideoInfo videoInfo=(VideoInfo)intent.getSerializableExtra("videoInfo");
        assert videoInfo != null;
        title=videoInfo.title;
        time=videoInfo.time;
        channel=videoInfo.channel;
        description=videoInfo.description;
        videoId=videoInfo.videoId;

        mYoutubePlayerView=findViewById(R.id.youtubeplay);
        titleTV = findViewById(R.id.videoPlayerTitle);
        timeTV = findViewById(R.id.videoPlayerTime);
        channelTV = findViewById(R.id.videoPlayerChannel);
        descriptionTV = findViewById(R.id.videoPlayerDescription);
        dropDown = findViewById(R.id.vpDescStatImg);
        layout = findViewById(R.id.videoPlayerTextMode);
        editText = findViewById(R.id.vpNotesEditText);
        mainLayout = findViewById(R.id.videoPlayerMainLayout);

        params = (LinearLayout.LayoutParams) descriptionTV.getLayoutParams();

        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo(videoId);
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

        FloatingActionButton button = findViewById(R.id.floatingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editNote){
                    editNote = true;
                    layout.setVisibility(View.VISIBLE);
                } else {
                    editNote = false;
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                    layout.setVisibility(View.INVISIBLE);
                    saveNote();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS notes ( id VARCHAR(25), title TEXT, data TEXT )");
        loadNote(videoId);

        mYoutubePlayerView.initialize("AIzaSyA0epWMVtHlvhZF2WsApIUFr_D0dFU_IY4",onInitializedListener);
        
        initializeTextView();
        System.out.println(videoId);
        mYoutubePlayerView.initialize("AIzaSyA0epWMVtHlvhZF2WsApIUFr_D0dFU_IY4",onInitializedListener);
//        title = "TITLE";
//        time = "TIME";
//        channel = "CHANNEL";

    }

    private void initializeTextView(){
        titleTV.setText(title);
        timeTV.setText(time);
        channelTV.setText(channel);
        descriptionTV.setText(description);
    }

    private void saveNote(){
        Cursor c = database.rawQuery("SELECT * FROM notes WHERE id = \"" + videoId + "\"", null);
        if(c!=null){
            c.moveToFirst();
            database.execSQL("DELETE FROM notes WHERE id = \"" + videoId + "\"");
        }
        assert c != null;
        database.execSQL("INSERT INTO notes (id, title, data) VALUES (" +
                String.format(Locale.US, "\"%s\", \"%s\", \"%s\")",
                        videoId, title, note));
        c.close();
    }

    private  void loadNote(String id){
        Cursor c = database.rawQuery("SELECT * FROM notes WHERE id = \"" + videoId + "\"", null);
        String savedNote = "";
        if(c!=null)
            c.moveToFirst();
        assert c != null;
        int index = c.getColumnIndex("data");
        if(c.getCount() > 0)
            savedNote = c.getString(index);
        editText.setText(savedNote);
        c.close();
    }
}