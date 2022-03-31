package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//These Changes Made by Gopal on 09-01-2021

public class GetSMSPayAPIResponse {
    @SerializedName("Amount")
    @Expose
    private Object amount;
    @SerializedName("CorpCode")
    @Expose
    private Object corpCode;
    @SerializedName("CustomerContactNumber")
    @Expose
    private Object customerContactNumber;
    @SerializedName("CustomerEmail")
    @Expose
    private Object customerEmail;
    @SerializedName("CustomerName")
    @Expose
    private Object customerName;
    @SerializedName("IsHBP")
    @Expose
    private Boolean isHBP;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("OperationType")
    @Expose
    private Object operationType;
    @SerializedName("OrderNumnber")
    @Expose
    private Object orderNumnber;
    @SerializedName("PaytmId")
    @Expose
    private String paytmId;
    @SerializedName("RequestType")
    @Expose
    private Object requestType;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("StoreId")
    @Expose
    private Object storeId;
    @SerializedName("TransactionId")
    @Expose
    private Object transactionId;
    @SerializedName("URL")
    @Expose
    private Object uRL;
    @SerializedName("orderId")
    @Expose
    private Object orderId;

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Object getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(Object corpCode) {
        this.corpCode = corpCode;
    }

    public Object getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(Object customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    public Object getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(Object customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Object getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Object customerName) {
        this.customerName = customerName;
    }

    public Boolean getIsHBP() {
        return isHBP;
    }

    public void setIsHBP(Boolean isHBP) {
        this.isHBP = isHBP;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getOperationType() {
        return operationType;
    }

    public void setOperationType(Object operationType) {
        this.operationType = operationType;
    }

    public Object getOrderNumnber() {
        return orderNumnber;
    }

    public void setOrderNumnber(Object orderNumnber) {
        this.orderNumnber = orderNumnber;
    }

    public String getPaytmId() {
        return paytmId;
    }

    public void setPaytmId(String paytmId) {
        this.paytmId = paytmId;
    }

    public Object getRequestType() {
        return requestType;
    }

    public void setRequestType(Object requestType) {
        this.requestType = requestType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getStoreId() {
        return storeId;
    }

    public void setStoreId(Object storeId) {
        this.storeId = storeId;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Object getURL() {
        return uRL;
    }

    public void setURL(Object uRL) {
        this.uRL = uRL;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }
}
