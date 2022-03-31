package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist_display_center {
    @Expose
    @SerializedName("uid")
    private String uid;
    @Expose
    @SerializedName("screens")
    private Screens screens;

    public String getUid() {
        return uid;
    }

    public Screens getScreens() {
        return screens;
    }
}
