package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyStaffApiRes {

    @Expose
    @SerializedName("ValidateOTP")
    private String ValidateOTP;
    @Expose
    @SerializedName("UsedBalance")
    private String UsedBalance;
    @Expose
    @SerializedName("TotalBalance")
    private String TotalBalance;
    @Expose
    @SerializedName("Status")
    private String Status;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("RegMobileNo")
    private String RegMobileNo;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("EmpName")
    private String EmpName;
    @Expose
    @SerializedName("Action")
    private String Action;
    @Expose
    @SerializedName("OTP")
    private String OTP;

    public String getValidateOTP() {
        return ValidateOTP;
    }

    public String getUsedBalance() {
        return UsedBalance;
    }

    public String getTotalBalance() {
        return TotalBalance;
    }

    public String getStatus() {
        return Status;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getRegMobileNo() {
        return RegMobileNo;
    }

    public String getMessage() {
        return Message;
    }

    public String getEmpName() {
        return EmpName;
    }

    public String getAction() {
        return Action;
    }

    public String getOTP() {
        return OTP;
    }
}
