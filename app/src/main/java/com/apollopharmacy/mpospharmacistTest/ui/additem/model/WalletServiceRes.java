package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletServiceRes {


    @Expose
    @SerializedName("WalletURL")
    private String WalletURL;
    @Expose
    @SerializedName("WalletType")
    private int WalletType;
    @Expose
    @SerializedName("WalletTransactionID")
    private String WalletTransactionID;
    @Expose
    @SerializedName("WalletRequestType")
    private int WalletRequestType;
    @Expose
    @SerializedName("WalletRefundId")
    private String WalletRefundId;
    @Expose
    @SerializedName("WalletOrderID")
    private String WalletOrderID;
    @Expose
    @SerializedName("WalletAmount")
    private double WalletAmount;
    @Expose
    @SerializedName("RewardsPoint")
    private int RewardsPoint;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("Response")
    private String Response;
    @Expose
    @SerializedName("RequestURL")
    private String RequestURL;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("POSTransactionID")
    private String POSTransactionID;
    @Expose
    @SerializedName("POSTerminal")
    private String POSTerminal;
    @Expose
    @SerializedName("OTPTransactionId")
    private String OTPTransactionId;
    @Expose
    @SerializedName("OTP")
    private String OTP;
    @Expose
    @SerializedName("MobileNo")
    private String MobileNo;

    public void setWalletURL(String WalletURL) {
        this.WalletURL = WalletURL;
    }

    public void setWalletType(int WalletType) {
        this.WalletType = WalletType;
    }

    public void setWalletTransactionID(String WalletTransactionID) {
        this.WalletTransactionID = WalletTransactionID;
    }

    public void setWalletRequestType(int WalletRequestType) {
        this.WalletRequestType = WalletRequestType;
    }

    public void setWalletRefundId(String WalletRefundId) {
        this.WalletRefundId = WalletRefundId;
    }

    public void setWalletOrderID(String WalletOrderID) {
        this.WalletOrderID = WalletOrderID;
    }

    public void setWalletAmount(int WalletAmount) {
        this.WalletAmount = WalletAmount;
    }

    public void setRewardsPoint(int RewardsPoint) {
        this.RewardsPoint = RewardsPoint;
    }

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    public void setRequestURL(String RequestURL) {
        this.RequestURL = RequestURL;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setPOSTransactionID(String POSTransactionID) {
        this.POSTransactionID = POSTransactionID;
    }

    public void setPOSTerminal(String POSTerminal) {
        this.POSTerminal = POSTerminal;
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

    public String getWalletURL() {
        return WalletURL;
    }

    public int getWalletType() {
        return WalletType;
    }

    public String getWalletTransactionID() {
        return WalletTransactionID;
    }

    public int getWalletRequestType() {
        return WalletRequestType;
    }

    public String getWalletRefundId() {
        return WalletRefundId;
    }

    public String getWalletOrderID() {
        return WalletOrderID;
    }

    public double getWalletAmount() {
        return WalletAmount;
    }

    public int getRewardsPoint() {
        return RewardsPoint;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public String getResponse() {
        return Response;
    }

    public String getRequestURL() {
        return RequestURL;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getPOSTransactionID() {
        return POSTransactionID;
    }

    public String getPOSTerminal() {
        return POSTerminal;
    }

    public String getOTPTransactionId() {
        return OTPTransactionId;
    }

    public String getOTP() {
        return OTP;
    }

    public String getMobileNo() {
        return MobileNo;
    }
}
