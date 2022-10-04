package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OMSTransactionRequest {
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

}
