package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorSearchReqModel {
    @Expose
    @SerializedName("ISAX")
    private boolean ISAX;
    @Expose
    @SerializedName("DoctorID")
    private String DoctorID;
    @Expose
    @SerializedName("DoctorName")
    private String DoctorName;
    @Expose
    @SerializedName("ClusterId")
    private String ClusterId;
    @Expose
    @SerializedName("DoctorBaseUrl")
    private String DoctorBaseUrl;

    public void setISAX(boolean ISAX) {
        this.ISAX = ISAX;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public void setClusterId(String clusterId) {
        ClusterId = clusterId;
    }

    public void setDoctorBaseUrl(String doctorBaseUrl) {
        DoctorBaseUrl = doctorBaseUrl;
    }
}
