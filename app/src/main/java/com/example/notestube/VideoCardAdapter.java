package com.example.notestube;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class VideoCardAdapter extends RecyclerView.Adapter<VideoCardAdapter.viewHolder> {

    ArrayList<String> videoTitle, channelName, timeStamps, links, descriptions;
    ArrayList<Bitmap> thumbNail;
    Context context;

    public VideoCardAdapter(Context ct, ArrayList<String> vT, ArrayList<String> cN, ArrayList<String> tS, ArrayList<Bitmap> tbNail, ArrayList<String> lk, ArrayList<String> desc){
        context = ct;
        videoTitle = vT;
        channelName = cN;
        timeStamps = tS;
        thumbNail = tbNail;
        links = lk;
        descriptions = desc;
    }

    @NonNull
    @Override
    public VideoCardAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCardAdapter.viewHolder holder, final int position) {
        holder.videoTitle.setText(videoTitle.get(position));
        holder.channelName.setText(channelName.get(position));
        holder.timeStamp.setText(timeStamps.get(position));
        holder.thumbnail.setImageBitmap(thumbNail.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, links.get(position), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,VideoPlayer.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(videoTitle != null)
            return videoTitle.size();
        else
            return 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView videoTitle, channelName, timeStamp;
        ImageView thumbnail;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.cardVideoTitle);
            channelName = itemView.findViewById(R.id.cardChannelName);
            timeStamp = itemView.findViewById(R.id.cardTimeDisplay);
            thumbnail = itemView.findViewById(R.id.cardThumbnail);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
