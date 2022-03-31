package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OMSOrderUpdateRequest
{
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
    private ArrayList<SalesLineEntity> reservedSalesLine = null;

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

    public ArrayList<SalesLineEntity> getReservedSalesLine() {
        return reservedSalesLine;
    }

    public void setReservedSalesLine(ArrayList<SalesLineEntity> reservedSalesLine) {
        this.reservedSalesLine = reservedSalesLine;
    }

}
