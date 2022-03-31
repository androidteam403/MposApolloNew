package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhonepeGenerateQrCodeResponse implements Serializable
{
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("QrCode")
    @Expose
    private String qrCode;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("providerReferenceId")
    @Expose
    private Object providerReferenceId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getProviderReferenceId() {
        return providerReferenceId;
    }

    public void setProviderReferenceId(Object providerReferenceId) {
        this.providerReferenceId = providerReferenceId;
    }
}
