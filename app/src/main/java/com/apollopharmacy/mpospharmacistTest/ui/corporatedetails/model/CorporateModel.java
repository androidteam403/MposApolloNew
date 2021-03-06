package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CorporateModel implements Serializable {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("_DropdownValue")
    private ArrayList<DropdownValueBean> _DropdownValue;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public ArrayList<DropdownValueBean> get_DropdownValue() {
        return _DropdownValue;
    }

    public static class DropdownValueBean implements Serializable {
        @Expose
        @SerializedName("Code")
        private String code;
        @Expose
        @SerializedName("CustState")
        private String custState;
        @Expose
        @SerializedName("Description")
        private String description;
        @Expose
        @SerializedName("PayMode")
        private String payMode;
        @Expose
        @SerializedName("SEZ")
        private String sEZ;

        public String getCode() {
            return code;
        }

        public String getCustState() {
            return custState;
        }

        public String getDescription() {
            return description;
        }

        public String getPayMode() {
            return payMode;
        }

        public String getsEZ() {
            return sEZ;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setCustState(String custState) {
            this.custState = custState;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPayMode(String payMode) {
            this.payMode = payMode;
        }

        public void setsEZ(String sEZ) {
            this.sEZ = sEZ;
        }

        private String prg_Tracking;

        public String getPrg_Tracking() {
            return prg_Tracking;
        }

        public void setPrg_Tracking(String prg_Tracking) {
            this.prg_Tracking = prg_Tracking;
        }
    }
}
