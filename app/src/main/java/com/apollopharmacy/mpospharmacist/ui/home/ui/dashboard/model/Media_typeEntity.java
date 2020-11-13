package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media_typeEntity {
    @Expose
    @SerializedName("other")
    private OtherEntity other;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("uid")
    private String uid;

    public OtherEntity getOther() {
        return other;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
