package com.apollopharmacy.mpospharmacist.ui.doctordetails.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorSearchResModel {
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
