package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidatePointsResModel {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("OneApolloProcessResult")
    private OneApolloProcessResultEntity OneApolloProcessResult;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public OneApolloProcessResultEntity getOneApolloProcessResult() {
        return OneApolloProcessResult;
    }

    public static class OneApolloProcessResultEntity {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("VoucherCode")
        private String VoucherCode;
        @Expose
        @SerializedName("Tier")
        private String Tier;
        @Expose
        @SerializedName("RedeemPoints")
        private String RedeemPoints;
        @Expose
        @SerializedName("RRNO")
        private String RRNO;
        @Expose
        @SerializedName("OTP")
        private String OTP;
        @Expose
        @SerializedName("Name")
        private String Name;
        @Expose
        @SerializedName("MobileNum")
        private String MobileNum;
        @Expose
        @SerializedName("Message")
        private String Message;
        @Expose
        @SerializedName("DiscountPercentage")
        private String DiscountPercentage;
        @Expose
        @SerializedName("DiscountAmount")
        private double DiscountAmount;
        @Expose
        @SerializedName("AvailablePoints")
        private String AvailablePoints;
        @Expose
        @SerializedName("Action")
        private String Action;

        public String getStatus() {
            return status;
        }

        public String getVoucherCode() {
            return VoucherCode;
        }

        public String getTier() {
            return Tier;
        }

        public String getRedeemPoints() {
            return RedeemPoints;
        }

        public String getRRNO() {
            return RRNO;
        }

        public String getOTP() {
            return OTP;
        }

        public String getName() {
            return Name;
        }

        public String getMobileNum() {
            return MobileNum;
        }

        public String getMessage() {
            return Message;
        }

        public String getDiscountPercentage() {
            return DiscountPercentage;
        }

        public double getDiscountAmount() {
            return DiscountAmount;
        }

        public String getAvailablePoints() {
            return AvailablePoints;
        }

        public String getAction() {
            return Action;
        }
    }
}
