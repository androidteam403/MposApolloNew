package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("code")
    private String code;
    @SerializedName("uid")
    private String uid;

    public String getCode() {
        return code;
    }

    public String getUid() {
        return uid;
    }
}
