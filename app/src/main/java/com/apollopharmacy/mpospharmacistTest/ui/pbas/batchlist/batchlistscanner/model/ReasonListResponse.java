package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReasonListResponse implements Serializable {
    @SerializedName("DataAreaID")
    @Expose
    private String dataAreaID;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("VendorId")
    @Expose
    private String vendorId;
    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("response")
    @Expose
    private Object response;

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

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public static class Datum implements Serializable {
        @SerializedName("reasonAvailable")
        @Expose
        private Integer reasonAvailable;
        @SerializedName("reasonBase")
        @Expose
        private String reasonBase;
        @SerializedName("reasonCode")
        @Expose
        private String reasonCode;
        @SerializedName("reasonDesc")
        @Expose
        private String reasonDesc;
        @SerializedName("vendorId")
        @Expose
        private Object vendorId;

        public Integer getReasonAvailable() {
            return reasonAvailable;
        }

        public void setReasonAvailable(Integer reasonAvailable) {
            this.reasonAvailable = reasonAvailable;
        }

        public String getReasonBase() {
            return reasonBase;
        }

        public void setReasonBase(String reasonBase) {
            this.reasonBase = reasonBase;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public String getReasonDesc() {
            return reasonDesc;
        }

        public void setReasonDesc(String reasonDesc) {
            this.reasonDesc = reasonDesc;
        }

        public Object getVendorId() {
            return vendorId;
        }

        public void setVendorId(Object vendorId) {
            this.vendorId = vendorId;
        }

    }
}
