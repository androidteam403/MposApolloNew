package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReasonListRequest implements Serializable {
    @SerializedName("VendorId")
    @Expose
    private String vendorId;
    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("DataAreaID")
    @Expose
    private String dataAreaID;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("data")
    @Expose
    private Object data;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getDataAreaID() {
        return dataAreaID;
    }

    public void setDataAreaID(String dataAreaID) {
        this.dataAreaID = dataAreaID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
