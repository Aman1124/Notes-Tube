package com.example.notestube;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class VideoCardAdapter extends RecyclerView.Adapter<VideoCardAdapter.viewHolder> {

    String[] videoTitle, channelName, timeStamps;
    Bitmap[] thumbNail;
    Context context;

    public VideoCardAdapter(Context ct, String[] vT, String[] cN, String[] tS, Bitmap[] tbNail){
        context = ct;
        videoTitle = vT;
        channelName = cN;
        timeStamps = tS;
        thumbNail = tbNail;
    }

    @NonNull
    @Override
    public VideoCardAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCardAdapter.viewHolder holder, int position) {
        holder.videoTitle.setText(videoTitle[position]);
        holder.channelName.setText(channelName[position]);
        holder.timeStamp.setText(timeStamps[position]);
        holder.thumbnail.setImageBitmap(thumbNail[position]);
    }

    @Override
    public int getItemCount() {
        return videoTitle.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView videoTitle, channelName, timeStamp;
        ImageView thumbnail;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.cardVideoTitle);
            channelName = itemView.findViewById(R.id.cardChannelName);
            timeStamp = itemView.findViewById(R.id.cardTimeDisplay);
            thumbnail = itemView.findViewById(R.id.cardThumbnail);
        }
    }
}
