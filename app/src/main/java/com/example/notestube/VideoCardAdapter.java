package com.example.notestube;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class VideoCardAdapter extends RecyclerView.Adapter<VideoCardAdapter.viewHolder> {

    ArrayList<String> videoTitle, channelName, timeStamps, links, descriptions;
    ArrayList<String> thumbNail;
    Context context;

    public VideoCardAdapter(Context ct, ArrayList<String> vT, ArrayList<String> cN, ArrayList<String> tS,
                            ArrayList<String> tbNail, ArrayList<String> lk, ArrayList<String> desc){
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
        holder.thumbnail.setImageBitmap(getImageBitmap(thumbNail.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, links.get(position), Toast.LENGTH_SHORT).show();
                VideoInfo videoInfo=new VideoInfo(links.get(position),videoTitle.get(position),descriptions.get(position),timeStamps.get(position),channelName.get(position));
                Intent intent=new Intent(context,VideoPlayer.class);
                intent.putExtra("videoInfo",videoInfo);
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

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Bitmap getImageBitmap(String url){
        VideoCardAdapter.ImageDownloader imageTask = new VideoCardAdapter.ImageDownloader();
        Bitmap imgBitmap = null;

        try {
            imgBitmap = imageTask.execute(url).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return imgBitmap;
    }
}
