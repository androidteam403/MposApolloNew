package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {

    @Expose
    @SerializedName("GetLoginUserResult")
    private GetLoginUserResultBean GetLoginUserResult;
    public String userType;

    public GetLoginUserResultBean getGetLoginUserResult() {
        return GetLoginUserResult;
    }


    public static class GetLoginUserResultBean {
        @Expose
        @SerializedName("_DropdownValue")
        private List<_DropdownValueBean> _DropdownValue;
        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;

        public List<_DropdownValueBean> get_DropdownValue() {
            return _DropdownValue;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public int getRequestStatus() {
            return RequestStatus;
        }
    }

    public static class _DropdownValueBean {
        @Expose
        @SerializedName("displayText")
        private String displayText;
        @Expose
        @SerializedName("code")
        private String code;

        public String getDisplayText() {
            return displayText;
        }

        public String getCode() {
            return code;
        }
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }


}
