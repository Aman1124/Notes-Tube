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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.local.QueryResult;

import java.util.ArrayList;


public class History extends Fragment {

    String[] videoTitle, channelName, thumbNail;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    ArrayList<VideoInfo> historyList;
    ArrayList<String> videoTitle1, channelName1,thumbnail1;
    int i;

    RecyclerView recyclerView;

    public History() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        historyList = new ArrayList<VideoInfo>();
        videoTitle1 = new ArrayList<String>();
        channelName1 = new ArrayList<String>();
        thumbnail1= new ArrayList<String>();
        mAuth=FirebaseAuth.getInstance();
        final String userId=mAuth.getUid();

        Query firstQuery = firestore.collection("videos");
        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Error: " + error.getMessage());
                } else {
                    assert value != null;
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            VideoInfo videoInfo = doc.getDocument().toObject(VideoInfo.class);
                            assert userId != null;
                            System.out.println(userId);
                            System.out.println(videoInfo.userid);
                            if(userId.equals(videoInfo.userid) && !videoTitle1.contains(videoInfo.title)) {
//                                System.out.println("USER IDS MATCHED");
                                historyList.add(videoInfo);
                                videoTitle1.add(videoInfo.title);
                                channelName1.add(videoInfo.channel);
                                thumbnail1.add(videoInfo.thumbnail);
                            }
                            else
                            {
                                System.out.println("DID NOT MATCH");
                            }
                            Log.i("videoTitle", "inside:" + String.valueOf(videoTitle1));
                        }
                    }

                    videoTitle = videoTitle1.toArray(new String[0]);
                    channelName = channelName1.toArray(new String[0]);
                    thumbNail= thumbnail1.toArray(new String[0]);
                    createLayout();
                }
            }
        });


        if (historyList.isEmpty()) {
            Toast.makeText(getContext(), "Empty List", Toast.LENGTH_SHORT).show();
        }
//    else
//        System.out.println(videoTitle1.get(0));
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

        Log.i("videoTitle", "outside:" + String.valueOf(videoTitle1));
        super.onActivityCreated(savedInstanceState);
    }

    public void createLayout() {
        HistoryCardAdapter myAdapter = new HistoryCardAdapter(getActivity(), videoTitle, channelName, thumbNail);
        Log.i("HISTORY", String.valueOf(myAdapter.getItemCount()));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}