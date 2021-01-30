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

public class HistoryCardAdapter extends RecyclerView.Adapter<HistoryCardAdapter.viewHolder> {

    String[] videoTitle, channelName;
    Bitmap[] thumbNail;
    Context context;

    public HistoryCardAdapter(Context ct, String[] vT, String[] cN, Bitmap[] tbNail){
        context = ct;
        videoTitle = vT;
        channelName = cN;
        thumbNail = tbNail;
    }

    @NonNull
    @Override
    public HistoryCardAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCardAdapter.viewHolder holder, int position) {
        holder.videoTitle.setText(videoTitle[position]);
        holder.channelName.setText(channelName[position]);
        holder.thumbnail.setImageBitmap(thumbNail[position]);
    }

    @Override
    public int getItemCount() {
        return videoTitle.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView videoTitle, channelName;
        ImageView thumbnail;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.historyCardVideoTitle);
            channelName = itemView.findViewById(R.id.historyCardChannelName);
            thumbnail = itemView.findViewById(R.id.historyCardThumbnail);
        }
    }
}
