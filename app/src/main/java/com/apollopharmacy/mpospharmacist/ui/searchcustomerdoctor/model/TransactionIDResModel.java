package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model;

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
}
