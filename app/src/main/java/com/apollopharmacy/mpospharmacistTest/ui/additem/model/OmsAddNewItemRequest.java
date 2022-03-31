package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmsAddNewItemRequest {
    @SerializedName("FullfillmentOrderID")
    @Expose
    private String fullfillmentOrderID;
    @SerializedName("ItemID")
    @Expose
    private String itemID;
    @SerializedName("Qty")
    @Expose
    private Double qty;
    @SerializedName("DiscPer")
    @Expose
    private Integer discPer;
    @SerializedName("StockQty")
    @Expose
    private Double stockQty;
    @SerializedName("OmsLineID")
    @Expose
    private Integer omsLineID;
    @SerializedName("OmsLineRECID")
    @Expose
    private Integer omsLineRECID;
    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("TerminalID")
    @Expose
    private String terminalID;
    @SerializedName("DataAreaID")
    @Expose
    private String dataAreaID;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public String getFullfillmentOrderID() {
        return fullfillmentOrderID;
    }

    public void setFullfillmentOrderID(String fullfillmentOrderID) {
        this.fullfillmentOrderID = fullfillmentOrderID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public Integer getDiscPer() {
        return discPer;
    }

    public void setDiscPer(Integer discPer) {
        this.discPer = discPer;
    }

    public Double getStockQty() {
        return stockQty;
    }

    public void setStockQty(Double stockQty) {
        this.stockQty = stockQty;
    }

    public Integer getOmsLineID() {
        return omsLineID;
    }

    public void setOmsLineID(Integer omsLineID) {
        this.omsLineID = omsLineID;
    }

    public Integer getOmsLineRECID() {
        return omsLineRECID;
    }

    public void setOmsLineRECID(Integer omsLineRECID) {
        this.omsLineRECID = omsLineRECID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getDataAreaID() {
        return dataAreaID;
    }

    public void setDataAreaID(String dataAreaID) {
        this.dataAreaID = dataAreaID;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
