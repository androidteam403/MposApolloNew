package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OMSOrderForwardResponse {
    @SerializedName("FulfillmentID")
    @Expose
    private String fulfillmentID;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("RequestType")
    @Expose
    private Integer requestType;
    @SerializedName("ReservedSalesLine")
    @Expose
    private List<GetOMSTransactionResponse.SalesLine> reservedSalesLine = null;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public String getFulfillmentID() {
        return fulfillmentID;
    }

    public void setFulfillmentID(String fulfillmentID) {
        this.fulfillmentID = fulfillmentID;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public List<GetOMSTransactionResponse.SalesLine> getReservedSalesLine() {
        return reservedSalesLine;
    }

    public void setReservedSalesLine(List<GetOMSTransactionResponse.SalesLine> reservedSalesLine) {
        this.reservedSalesLine = reservedSalesLine;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
