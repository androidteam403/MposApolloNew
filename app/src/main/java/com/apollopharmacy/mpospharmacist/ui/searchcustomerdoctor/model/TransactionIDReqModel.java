package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionIDReqModel {
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
    private int billingMode;

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

    public void setBillingMode(int billingMode) {
        this.billingMode = billingMode;
    }
}
