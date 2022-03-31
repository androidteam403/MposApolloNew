package com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoginResModel {
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("clusterId")
    private String clusterId;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getClusterId() {
        return clusterId;
    }
}
