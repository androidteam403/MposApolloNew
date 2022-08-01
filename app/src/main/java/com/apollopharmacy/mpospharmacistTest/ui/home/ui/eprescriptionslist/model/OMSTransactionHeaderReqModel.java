package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OMSTransactionHeaderReqModel {
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;
    @Expose
    @SerializedName("TerminalID")
    private String TerminalID;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("ExpiryDays")
    private int ExpiryDays;
    @Expose
    @SerializedName("RefID")
    private String RefID;
    @Expose
    @SerializedName("TransactionID")
    private String TransactionID;
    @Expose
    private String IsMPOS;
    @SerializedName("UserName")
    @Expose
    private String UserName;
    public void setDataAreaID(String DataAreaID) {
        this.DataAreaID = DataAreaID;
    }

    public void setTerminalID(String TerminalID) {
        this.TerminalID = TerminalID;
    }

    public void setStoreID(String StoreID) {
        this.StoreID = StoreID;
    }

    public void setExpiryDays(int ExpiryDays) {
        this.ExpiryDays = ExpiryDays;
    }

    public void setRefID(String RefID) {
        this.RefID = RefID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getIsMPOS() {
        return IsMPOS;
    }

    public void setIsMPOS(String isMPOS) {
        IsMPOS = isMPOS;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
