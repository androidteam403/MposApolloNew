package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet {

        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;
        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;
        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;
        @Expose
        @SerializedName("WalletOrderID")
        private String WalletOrderID;
        @Expose
        @SerializedName("WalletRefundId")
        private String WalletRefundId;
        @Expose
        @SerializedName("WalletAmount")
        private double WalletAmount;
        @Expose
        @SerializedName("POSTerminal")
        private String POSTerminal;
        @Expose
        @SerializedName("POSTransactionID")
        private String POSTransactionID;
        @Expose
        @SerializedName("Response")
        private String Response;
        @Expose
        @SerializedName("RequestURL")
        private String RequestURL;
        @Expose
        @SerializedName("OTPTransactionId")
        private String OTPTransactionId;
        @Expose
        @SerializedName("OTP")
        private String OTP;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("WalletURL")
        private String WalletURL;
        @Expose
        @SerializedName("WalletRequestType")
        private int WalletRequestType;
        @Expose
        @SerializedName("WalletType")
        private int WalletType;

        public void setReturnMessage(String ReturnMessage) {
            this.ReturnMessage = ReturnMessage;
        }

        public void setRequestStatus(int RequestStatus) {
            this.RequestStatus = RequestStatus;
        }

        public void setRewardsPoint(int RewardsPoint) {
            this.RewardsPoint = RewardsPoint;
        }

        public void setWalletTransactionID(String WalletTransactionID) {
            this.WalletTransactionID = WalletTransactionID;
        }

        public void setWalletOrderID(String WalletOrderID) {
            this.WalletOrderID = WalletOrderID;
        }

        public void setWalletRefundId(String WalletRefundId) {
            this.WalletRefundId = WalletRefundId;
        }

        public void setWalletAmount(double WalletAmount) {
            this.WalletAmount = WalletAmount;
        }

        public void setPOSTerminal(String POSTerminal) {
            this.POSTerminal = POSTerminal;
        }

        public void setPOSTransactionID(String POSTransactionID) {
            this.POSTransactionID = POSTransactionID;
        }

        public void setResponse(String Response) {
            this.Response = Response;
        }

        public void setRequestURL(String RequestURL) {
            this.RequestURL = RequestURL;
        }

        public void setOTPTransactionId(String OTPTransactionId) {
            this.OTPTransactionId = OTPTransactionId;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public void setWalletURL(String WalletURL) {
            this.WalletURL = WalletURL;
        }

        public void setWalletRequestType(int WalletRequestType) {
            this.WalletRequestType = WalletRequestType;
        }

        public void setWalletType(int WalletType) {
            this.WalletType = WalletType;
        }
    }