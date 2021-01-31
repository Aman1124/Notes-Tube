package com.example.notestube;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class VideoInfo implements Serializable {

    public String videoId;
    public String title;
    public String description;
    public String time;
    public String channel;
    public String thumbnail;
    public String userid;

    public VideoInfo(String videoId, String title, String description, String time, String channel,String thumbnail, String userid) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.channel = channel;
        this.thumbnail=thumbnail;
        this.userid=userid;
    }

    public VideoInfo() {

    }

}
