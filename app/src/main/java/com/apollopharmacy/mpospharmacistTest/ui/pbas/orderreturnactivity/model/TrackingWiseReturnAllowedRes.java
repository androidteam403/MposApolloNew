package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackingWiseReturnAllowedRes {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("ResultValue")
    private String ResultValue;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public String getResultValue() {
        return ResultValue;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }
}
