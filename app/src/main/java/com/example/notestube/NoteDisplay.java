package com.example.notestube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class NoteDisplay extends AppCompatActivity {

    String note = "";
    TextView noteTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        Intent intent = getIntent();

        noteTV = findViewById(R.id.noteTextView);

        String videoId = intent.getStringExtra("videoID");
        assert videoId != null;
        if(!videoId.equals(""))
            loadNote(videoId);
    }

    private void loadNote(String videoId) {
        SQLiteDatabase database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null);
        Cursor c = database.rawQuery("SELECT * FROM notes WHERE id = \"" + videoId + "\"", null);
        if(c!=null)
            c.moveToFirst();
        assert c != null;
        int noteIndex = c.getColumnIndex("data");
        try{
            note = c.getString(noteIndex);
        } catch (Exception e){
            e.printStackTrace();
        }
        noteTV.setText(note);
        c.close();
    }
}