package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist_mediaEntity {
    @Expose
    @SerializedName("media_library")
    private Media_libraryEntity media_library;
    @Expose
    @SerializedName("order")
    private int order;
    @Expose
    @SerializedName("no_of_times")
    private int no_of_times;
    @Expose
    @SerializedName("uid")
    private String uid;

    public Media_libraryEntity getMedia_library() {
        return media_library;
    }

    public int getOrder() {
        return order;
    }

    public int getNo_of_times() {
        return no_of_times;
    }

    public String getUid() {
        return uid;
    }

    public void setMedia_library(Media_libraryEntity media_library) {
        this.media_library = media_library;
    }
}
