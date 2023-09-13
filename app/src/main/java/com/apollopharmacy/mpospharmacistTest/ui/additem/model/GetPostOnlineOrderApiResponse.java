package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPostOnlineOrderApiResponse {

    @SerializedName("CashToBeCollected")
    @Expose
    private Object cashToBeCollected;
    @SerializedName("CreditAmount")
    @Expose
    private Object creditAmount;
    @SerializedName("IsCod")
    @Expose
    private Boolean isCod;
    @SerializedName("IsDiscount")
    @Expose
    private Boolean isDiscount;
    @SerializedName("IsVoidItem")
    @Expose
    private Boolean isVoidItem;
    @SerializedName("ItemDetail")
    @Expose
    private Object itemDetail;
    @SerializedName("RequestMessage")
    @Expose
    private String requestMessage;
    @SerializedName("RequestStatus")
    @Expose
    private Boolean requestStatus;

    public Object getCashToBeCollected() {
        return cashToBeCollected;
    }

    public void setCashToBeCollected(Object cashToBeCollected) {
        this.cashToBeCollected = cashToBeCollected;
    }

    public Object getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Object creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    public Boolean getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Boolean isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Boolean getIsVoidItem() {
        return isVoidItem;
    }

    public void setIsVoidItem(Boolean isVoidItem) {
        this.isVoidItem = isVoidItem;
    }

    public Object getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(Object itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Boolean getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

}
