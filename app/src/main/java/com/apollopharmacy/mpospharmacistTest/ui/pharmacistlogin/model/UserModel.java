package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
        private ArrayList<_DropdownValueBean> _DropdownValue;
        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;

        public ArrayList<_DropdownValueBean> get_DropdownValue() {
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

        @Expose
        @SerializedName("WeeklyThreshold")
        private String weeklyThreshold;

        @Expose
        @SerializedName("UserType")
        private String userType;

        @Expose
        @SerializedName("UserCategory")
        private String userCategory;

        @Expose
        @SerializedName("MinimumOrders")
        private String minimumOrders;

        @Expose
        @SerializedName("MaximumOrders")
        private String maximumOrders;

        public String getDisplayText() {
            return displayText;
        }

        public String getCode() {
            return code;
        }

        public String getWeeklyThreshold() {
            return weeklyThreshold;
        }

        public void setWeeklyThreshold(String weeklyThreshold) {
            this.weeklyThreshold = weeklyThreshold;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserCategory() {
            return userCategory;
        }

        public void setUserCategory(String userCategory) {
            this.userCategory = userCategory;
        }

        public String getMinimumOrders() {
            return minimumOrders;
        }

        public void setMinimumOrders(String minimumOrders) {
            this.minimumOrders = minimumOrders;
        }

        public String getMaximumOrders() {
            return maximumOrders;
        }

        public void setMaximumOrders(String maximumOrders) {
            this.maximumOrders = maximumOrders;
        }

        @Override
        public String toString() {
            return  displayText;
        }
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
