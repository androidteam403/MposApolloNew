package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowsEntity {
    @Expose
    @SerializedName("end_time")
    private String endTime;
    @Expose
    @SerializedName("end_date")
    private String end_date;
    @Expose
    @SerializedName("start_time")
    private String start_time;
    @Expose
    @SerializedName("play_date")
    private String play_date;
    @Expose
    @SerializedName("playlist_media")
    private Playlist_mediaEntity playlist_media;
    @Expose
    @SerializedName("playlist_display_center")
    private Playlist_display_center playlist_display_center;
    @Expose
    @SerializedName("is_loop")
    private boolean isLoop;
    @Expose
    @SerializedName("no_of_times")
    private int noOfTimes;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("uid")
    private String uid;

    private boolean isPlayed;

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getPlay_date() {
        return play_date;
    }

    public Playlist_mediaEntity getPlaylist_media() {
        return playlist_media;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public int getNoOfTimes() {
        return noOfTimes;
    }

    public String getEndTime() {
        return endTime;
    }

    public Playlist_display_center getPlaylist_display_center() {
        return playlist_display_center;
    }
}
