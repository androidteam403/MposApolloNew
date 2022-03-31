package com.apollopharmacy.mpospharmacistTest.ui.adddoctor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDoctorReqModel {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("ClusterCode")
    private String ClusterCode;
    @Expose
    @SerializedName("DoctorCreationURL")
    private String DoctorCreationURL;
    @Expose
    @SerializedName("AXPassword")
    private String AXPassword;
    @Expose
    @SerializedName("AXUserId")
    private String AXUserId;
    @Expose
    @SerializedName("AXDomain")
    private String AXDomain;
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;
    @Expose
    @SerializedName("RECID")
    private String RECID;
    @Expose
    @SerializedName("StoreId")
    private String StoreId;
    @Expose
    @SerializedName("PhoneNo")
    private String PhoneNo;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("Address")
    private String Address;
    @Expose
    @SerializedName("PracticePlace")
    private String PracticePlace;
    @Expose
    @SerializedName("Speciality")
    private String Speciality;
    @Expose
    @SerializedName("DocName")
    private String DocName;
    @Expose
    @SerializedName("DocAXID")
    private String DocAXID;
    @Expose
    @SerializedName("DocRegID")
    private String DocRegID;

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setClusterCode(String ClusterCode) {
        this.ClusterCode = ClusterCode;
    }

    public void setDoctorCreationURL(String DoctorCreationURL) {
        this.DoctorCreationURL = DoctorCreationURL;
    }

    public void setAXPassword(String AXPassword) {
        this.AXPassword = AXPassword;
    }

    public void setAXUserId(String AXUserId) {
        this.AXUserId = AXUserId;
    }

    public void setAXDomain(String AXDomain) {
        this.AXDomain = AXDomain;
    }

    public void setDataAreaID(String DataAreaID) {
        this.DataAreaID = DataAreaID;
    }

    public void setRECID(String RECID) {
        this.RECID = RECID;
    }

    public void setStoreId(String StoreId) {
        this.StoreId = StoreId;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setPracticePlace(String PracticePlace) {
        this.PracticePlace = PracticePlace;
    }

    public void setSpeciality(String Speciality) {
        this.Speciality = Speciality;
    }

    public void setDocName(String DocName) {
        this.DocName = DocName;
    }

    public void setDocAXID(String DocAXID) {
        this.DocAXID = DocAXID;
    }

    public void setDocRegID(String DocRegID) {
        this.DocRegID = DocRegID;
    }
}
