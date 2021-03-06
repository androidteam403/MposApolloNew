package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SalesOriginResModel {
    @Expose
    @SerializedName("GetSalesOriginResult")
    private GetSalesOriginResult getSalesOriginResult;

    public GetSalesOriginResult getGetSalesOriginResult() {
        return getSalesOriginResult;
    }

    public static class GetSalesOriginResult {
        @Expose
        @SerializedName("_DropdownValue")
        private ArrayList<DropdownValueBean> _DropdownValue;
        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;

        public ArrayList<DropdownValueBean> get_DropdownValue() {
            return _DropdownValue;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public int getRequestStatus() {
            return RequestStatus;
        }
    }

    public static class DropdownValueBean implements Serializable {
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("displayText")
        private String displayText;

        public String getCode() {
            return code;
        }

        public String getDisplayText() {
            return displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }
}
