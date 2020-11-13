package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class Display_center {
    @SerializedName("code")
    private String code;
    @SerializedName("city")
    private City city;
    @SerializedName("uid")
    private String uid;

    public String getCode() {
        return code;
    }

    public City getCity() {
        return city;
    }

    public String getUid() {
        return uid;
    }
}
