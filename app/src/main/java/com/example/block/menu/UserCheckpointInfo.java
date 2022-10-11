package com.example.block.menu;

public class UserCheckpointInfo {
    private String checkpointName,author;
    private int downloadTimes,likes;
    public UserCheckpointInfo(String checkpointName,String author,int downloadTimes,int likes)
    {
        this.checkpointName = checkpointName;
        this.author = author;
        this.downloadTimes = downloadTimes;
        this.likes = likes;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public String getAuthor() {
        return author;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public int getLikes() {
        return likes;
    }
}
