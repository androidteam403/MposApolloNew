package com.apollopharmacy.mpospharmacist.ui.corporatedetails.model;

import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CorporateModel {
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

    public static class DropdownValueBean {
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
    }
}
