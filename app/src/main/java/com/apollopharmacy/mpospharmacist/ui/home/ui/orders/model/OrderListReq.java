package com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class OrderListReq {


    @Expose
    @SerializedName("PendingBills")
    private boolean PendingBills;
    @Expose
    @SerializedName("PreviousBills")
    private boolean PreviousBills;
    @Expose
    @SerializedName("CardNo")
    private String CardNo;
    @Expose
    @SerializedName("IPNumber")
    private String IPNumber;
    @Expose
    @SerializedName("ArtName")
    private String ArtName;
    @Expose
    @SerializedName("BatchNo")
    private String BatchNo;
    @Expose
    @SerializedName("CustomerName")
    private String CustomerName;
    @Expose
    @SerializedName("MobileNo")
    private String MobileNo;
    @Expose
    @SerializedName("HomeDelivery")
    private boolean HomeDelivery;
    @Expose
    @SerializedName("ItemID")
    private String ItemID;
    @Expose
    @SerializedName("ReceiptId")
    private String ReceiptId;
    @Expose
    @SerializedName("CustomerAccount")
    private String CustomerAccount;
    @Expose
    @SerializedName("ToDate")
    private String ToDate;
    @Expose
    @SerializedName("FromDate")
    private String FromDate;

    public void setPendingBills(boolean PendingBills) {
        this.PendingBills = PendingBills;
    }

    public void setPreviousBills(boolean PreviousBills) {
        this.PreviousBills = PreviousBills;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public void setIPNumber(String IPNumber) {
        this.IPNumber = IPNumber;
    }

    public void setArtName(String ArtName) {
        this.ArtName = ArtName;
    }

    public void setBatchNo(String BatchNo) {
        this.BatchNo = BatchNo;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public void setHomeDelivery(boolean HomeDelivery) {
        this.HomeDelivery = HomeDelivery;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public void setReceiptId(String ReceiptId) {
        this.ReceiptId = ReceiptId;
    }

    public void setCustomerAccount(String CustomerAccount) {
        this.CustomerAccount = CustomerAccount;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }
}
