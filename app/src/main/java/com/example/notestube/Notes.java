package com.example.notestube;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class Notes extends Fragment {

    ArrayList<String> videoIDs = new ArrayList<>();
    ArrayList<String> notesHeading = new ArrayList<>();;
    ArrayAdapter<String> arrayAdapter;

    SQLiteDatabase database;

    public Notes() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = requireView().findViewById(R.id.notesListView);

        database = requireContext().openOrCreateDatabase("Notes", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS notes ( id VARCHAR(25), title TEXT, data TEXT )");
        videoIDs.clear(); notesHeading.clear();

        arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, notesHeading);

        listView.setAdapter(arrayAdapter);
        loadNotesHeading();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireContext(), NoteDisplay.class);
                intent.putExtra("videoID", videoIDs.get(position));
                startActivity(intent);
            }
        });
    }

    private void loadNotesHeading() {

        Cursor c = database.rawQuery("SELECT * FROM notes", null);
        if(c!=null)
            c.moveToFirst();
        assert c != null;
        int idIndex = c.getColumnIndex("id");
        int titleIndex = c.getColumnIndex("title");
        try{
            do{
                videoIDs.add(c.getString(idIndex));
                notesHeading.add(c.getString(titleIndex));
            } while (c.moveToNext());
        } catch (Exception e){
            e.printStackTrace();
        }
        arrayAdapter.notifyDataSetChanged();
        c.close();
    }
}