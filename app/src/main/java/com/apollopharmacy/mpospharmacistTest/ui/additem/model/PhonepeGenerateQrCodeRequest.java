package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhonepeGenerateQrCodeRequest implements Serializable
{
    @SerializedName("reqType")
    @Expose
    private String reqType;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("expiresIn")
    @Expose
    private Integer expiresIn;
    @SerializedName("providerReferenceId")
    @Expose
    private Object providerReferenceId;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("originalTransactionId")
    @Expose
    private Object originalTransactionId;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Object getProviderReferenceId() {
        return providerReferenceId;
    }

    public void setProviderReferenceId(Object providerReferenceId) {
        this.providerReferenceId = providerReferenceId;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getOriginalTransactionId() {
        return originalTransactionId;
    }

    public void setOriginalTransactionId(Object originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

}
