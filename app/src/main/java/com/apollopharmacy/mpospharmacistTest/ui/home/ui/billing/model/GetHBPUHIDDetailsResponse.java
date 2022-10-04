package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model;

import com.apollopharmacy.mpospharmacistTest.di.PreferenceInfo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetHBPUHIDDetailsResponse implements Serializable {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("uhid_details")
    private Uhid_details uhid_details;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public Uhid_details getUhid_details() {
        return uhid_details;
    }
}
