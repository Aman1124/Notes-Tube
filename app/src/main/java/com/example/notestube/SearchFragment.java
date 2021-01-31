package com.example.notestube;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    ArrayList<String> videoTitle, channelName, timeStamps, links, desc;
    ArrayList<String> thumbNail;

    RecyclerView recyclerView;

    public SearchFragment(ArrayList<String> vT, ArrayList<String> cN, ArrayList<String> tS, ArrayList<String> tbNail, ArrayList<String> lk, ArrayList<String> des) {
        videoTitle = vT;
        channelName = cN;
        timeStamps = tS;
        thumbNail = tbNail;
        links = lk;
        desc = des;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        recyclerView = requireView().findViewById(R.id.searchRecycler);
        createLayout();

        super.onActivityCreated(savedInstanceState);
    }

    public void createLayout(){
        VideoCardAdapter myAdapter = new VideoCardAdapter(getActivity(), videoTitle, channelName, timeStamps, thumbNail, links, desc);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}