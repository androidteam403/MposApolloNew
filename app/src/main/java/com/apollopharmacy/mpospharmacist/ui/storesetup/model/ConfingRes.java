package com.apollopharmacy.mpospharmacist.ui.storesetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfingRes {


    @Expose
    @SerializedName("TerminalID")
    private String TerminalID;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;

    public String getTerminalID() {
        return TerminalID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }
}
