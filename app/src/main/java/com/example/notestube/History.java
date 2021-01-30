package com.example.notestube;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class History extends Fragment {

    String[] videoTitle, channelName;
    Bitmap[] thumbNail;

    RecyclerView recyclerView;

    public History(String[] vT, String[] cN, Bitmap[] tbNail) {
        videoTitle = vT;
        channelName = cN;
        thumbNail = tbNail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        recyclerView = requireView().findViewById(R.id.historyRecyclerView);
        createLayout();

        super.onActivityCreated(savedInstanceState);
    }

    public void createLayout(){
        HistoryCardAdapter myAdapter = new HistoryCardAdapter(getActivity(), videoTitle, channelName, thumbNail);
        Log.i("HISTORY", String.valueOf(myAdapter.getItemCount()));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}