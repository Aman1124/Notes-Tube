package com.example.notestube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HistoryCardAdapter extends RecyclerView.Adapter<HistoryCardAdapter.viewHolder> {

    String[] videoTitle, channelName;
    String[] thumbNail;
    Context context;

    public HistoryCardAdapter(Context ct, String[] vT, String[] cN, String[] tbNail){
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
        //holder.thumbnail.setImageBitmap(getImageBitmap(thumbNail[position]));
    }

    @Override
    public int getItemCount() {
        if(videoTitle != null)
            return videoTitle.length;
        else
            return 0;
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

    private static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private Bitmap getImageBitmap(String url){
        HistoryCardAdapter.ImageDownloader imageTask = new HistoryCardAdapter.ImageDownloader();
        Bitmap imgBitmap = null;

        try {
            imgBitmap = imageTask.execute(url).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return imgBitmap;
    }
}
