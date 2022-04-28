package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OMSOrderForwardRequest {
    @SerializedName("RequestType")
    @Expose
    private String requestType;

    @SerializedName("FulfillmentID")
    @Expose
    private String fulfillmentID;

    @SerializedName("TerminalID")
    @Expose
    private String terminalId;

    @SerializedName("ReservedSalesLine")
    @Expose
    private ArrayList<GetOMSTransactionResponse.SalesLine> reservedSalesLine = null;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getFulfillmentID() {
        return fulfillmentID;
    }

    public void setFulfillmentID(String fulfillmentID) {
        this.fulfillmentID = fulfillmentID;
    }


    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalID(String terminalId1) {
        this.terminalId = terminalId1;
    }

    public ArrayList<GetOMSTransactionResponse.SalesLine> getReservedSalesLine() {
        return reservedSalesLine;
    }

    public void setReservedSalesLine(ArrayList<GetOMSTransactionResponse.SalesLine> reservedSalesLine) {
        this.reservedSalesLine = reservedSalesLine;
    }
}
