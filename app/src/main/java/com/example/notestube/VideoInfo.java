package com.example.notestube;
import java.io.Serializable;

public class VideoInfo implements Serializable {
    public String videoId;
    public String title;
    public String description;
    public String time;
    public String channel;
    public VideoInfo(String videoId,String title,String description,String time,String channel){
        this.videoId=videoId;
        this.title=title;
        this.description=description;
        this.time=time;
        this.channel=channel;
    }
}
