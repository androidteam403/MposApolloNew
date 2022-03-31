package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//These Changes made by Gopal on 09-01-2021

public class GetSMSPayAPIRequest {
    @SerializedName("OperationType")
    @Expose
    private String operationType;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("CorpCode")
    @Expose
    private String corpCode;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("OrderNumnber")
    @Expose
    private String orderNumnber;
    @SerializedName("CustomerContactNumber")
    @Expose
    private String customerContactNumber;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("IsHBP")
    @Expose
    private Boolean isHBP;
    @SerializedName("StoreId")
    @Expose
    private String storeId;
    @SerializedName("PaytmId")
    @Expose
    private Object paytmId;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("orderId")
    @Expose
    private Object orderId;
    @SerializedName("URL")
    @Expose
    private String uRL;



    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNumnber() {
        return orderNumnber;
    }

    public void setOrderNumnber(String orderNumnber) {
        this.orderNumnber = orderNumnber;
    }

    public String getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Boolean getIsHBP() {
        return isHBP;
    }

    public void setIsHBP(Boolean isHBP) {
        this.isHBP = isHBP;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Object getPaytmId() {
        return paytmId;
    }

    public void setPaytmId(Object paytmId) {
        this.paytmId = paytmId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }
}
