package com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckBatchInventoryRes {


    @Expose
    @SerializedName("TerminalID")
    private String TerminalID;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("Stock")
    private int Stock;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("ItemID")
    private String ItemID;
    @Expose
    @SerializedName("InventBatchID")
    private String InventBatchID;
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;

    public String getTerminalID() {
        return TerminalID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public int getStock() {
        return Stock;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getInventBatchID() {
        return InventBatchID;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }
}
