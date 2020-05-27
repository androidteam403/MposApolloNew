package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyStaffAPIReq {


    @Expose
    @SerializedName("Url")
    private String Url;
    @Expose
    @SerializedName("EmpId")
    private String EmpId;
    @Expose
    @SerializedName("Action")
    private String Action;
    @Expose
    @SerializedName("OTP")
    private String OTP;
    @Expose
    @SerializedName("SiteName")
    private String SiteName;
    @Expose
    @SerializedName("Region")
    private String Region;
    @Expose
    @SerializedName("Amount")
    private double Amount;
    @Expose
    @SerializedName("MobileNum")
    private String MobileNum;
    @Expose
    @SerializedName("DocNum")
    private String DocNum;
    @Expose
    @SerializedName("SiteId")
    private String SiteId;

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setEmpId(String EmpId) {
        this.EmpId = EmpId;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public void setSiteName(String SiteName) {
        this.SiteName = SiteName;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public void setMobileNum(String MobileNum) {
        this.MobileNum = MobileNum;
    }

    public void setDocNum(String DocNum) {
        this.DocNum = DocNum;
    }

    public void setSiteId(String SiteId) {
        this.SiteId = SiteId;
    }
}
