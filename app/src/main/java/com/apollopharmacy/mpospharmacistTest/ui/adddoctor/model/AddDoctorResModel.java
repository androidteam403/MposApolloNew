package com.apollopharmacy.mpospharmacistTest.ui.adddoctor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDoctorResModel {
    @Expose
    @SerializedName("StoreId")
    private String StoreId;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("Speciality")
    private String Speciality;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("RECID")
    private String RECID;
    @Expose
    @SerializedName("PracticePlace")
    private String PracticePlace;
    @Expose
    @SerializedName("PhoneNo")
    private String PhoneNo;
    @Expose
    @SerializedName("DoctorCreationURL")
    private String DoctorCreationURL;
    @Expose
    @SerializedName("DocRegID")
    private String DocRegID;
    @Expose
    @SerializedName("DocName")
    private String DocName;
    @Expose
    @SerializedName("DocAXID")
    private String DocAXID;
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;
    @Expose
    @SerializedName("ClusterCode")
    private String ClusterCode;
    @Expose
    @SerializedName("Address")
    private String Address;
    @Expose
    @SerializedName("AXUserId")
    private String AXUserId;
    @Expose
    @SerializedName("AXPassword")
    private String AXPassword;
    @Expose
    @SerializedName("AXDomain")
    private String AXDomain;

    public String getStoreId() {
        return StoreId;
    }

    public String getState() {
        return State;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getRECID() {
        return RECID;
    }

    public String getPracticePlace() {
        return PracticePlace;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getDoctorCreationURL() {
        return DoctorCreationURL;
    }

    public String getDocRegID() {
        return DocRegID;
    }

    public String getDocName() {
        return DocName;
    }

    public String getDocAXID() {
        return DocAXID;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }

    public String getClusterCode() {
        return ClusterCode;
    }

    public String getAddress() {
        return Address;
    }

    public String getAXUserId() {
        return AXUserId;
    }

    public String getAXPassword() {
        return AXPassword;
    }

    public String getAXDomain() {
        return AXDomain;
    }
}
