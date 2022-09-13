package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class SendGlobalMessageResponse implements Serializable {


    @Expose
    @SerializedName("Status")
    private boolean Status;
    @Expose
    @SerializedName("Otp")
    private String Otp;
    @Expose
    @SerializedName("Message")
    private String Message;

    public boolean getStatus() {
        return Status;
    }

    public String getOtp() {
        return Otp;
    }

    public String getMessage() {
        return Message;
    }
}
