package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMobileNumVerify {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private MobileData data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MobileData getData() {
        return data;
    }

    public void setData(MobileData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
