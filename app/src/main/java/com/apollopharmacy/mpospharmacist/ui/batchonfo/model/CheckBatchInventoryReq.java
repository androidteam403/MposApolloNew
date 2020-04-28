package com.apollopharmacy.mpospharmacist.ui.batchonfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckBatchInventoryReq {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
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
    @SerializedName("Stock")
    private int Stock;
    @Expose
    @SerializedName("InventBatchID")
    private String InventBatchID;
    @Expose
    @SerializedName("ItemID")
    private String ItemID;

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setDataAreaID(String DataAreaID) {
        this.DataAreaID = DataAreaID;
    }

    public void setTerminalID(String TerminalID) {
        this.TerminalID = TerminalID;
    }

    public void setStoreID(String StoreID) {
        this.StoreID = StoreID;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public void setInventBatchID(String InventBatchID) {
        this.InventBatchID = InventBatchID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }
}
