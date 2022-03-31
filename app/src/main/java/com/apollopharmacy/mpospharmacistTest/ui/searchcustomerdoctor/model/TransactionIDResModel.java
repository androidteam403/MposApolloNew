package com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionIDResModel implements Serializable {
    @Expose
    @SerializedName("RequestStatus")
    private int requestStatus;
    @Expose
    @SerializedName("ReturnMessage")
    private String returnMessage;
    @Expose
    @SerializedName("ResultValue")
    private String resultValue;
    @Expose
    @SerializedName("TransactionID")
    private String transactionID;
    @Expose
    @SerializedName("StoreID")
    private String storeID;
    @Expose
    @SerializedName("TerminalID")
    private String terminalID;
    @Expose
    @SerializedName("DataAreaID")
    private String dataAreaID;
    @Expose
    @SerializedName("BillingMode")
    private String billingMode;

    public int getRequestStatus() {
        return requestStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public String getResultValue() {
        return resultValue;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getStoreID() {
        return storeID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public String getDataAreaID() {
        return dataAreaID;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public void setDataAreaID(String dataAreaID) {
        this.dataAreaID = dataAreaID;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }
}
