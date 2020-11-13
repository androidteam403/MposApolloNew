package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class Screens {
    @SerializedName("code")
    private String code;
    @SerializedName("display_center")
    private Display_center display_center;
    @SerializedName("uid")
    private String uid;

    public String getCode() {
        return code;
    }

    public Display_center getDisplay_center() {
        return display_center;
    }

    public String getUid() {
        return uid;
    }
}
