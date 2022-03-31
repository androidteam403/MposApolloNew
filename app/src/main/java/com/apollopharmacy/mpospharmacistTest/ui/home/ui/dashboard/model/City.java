package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("code")
    private String code;
    @SerializedName("region")
    private Region region;
    @SerializedName("uid")
    private String uid;

    public String getCode() {
        return code;
    }

    public Region getRegion() {
        return region;
    }

    public String getUid() {
        return uid;
    }
}
