package com.apollopharmacy.mpospharmacist.ui.storesetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceSetupReqModel {
    @Expose
    @SerializedName("LATITUDE")
    private String LATITUDE;
    @Expose
    @SerializedName("LOGITUDE")
    private String LOGITUDE;
    @Expose
    @SerializedName("DEVICEDATE")
    private String DEVICEDATE;
    @Expose
    @SerializedName("DEVICETYPE")
    private String DEVICETYPE;
    @Expose
    @SerializedName("USERID")
    private String USERID;
    @Expose
    @SerializedName("TERMINALID")
    private String TERMINALID;
    @Expose
    @SerializedName("STOREID")
    private String STOREID;
    @Expose
    @SerializedName("FCMKEY")
    private String FCMKEY;
    @Expose
    @SerializedName("MACID")
    private String MACID;

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public void setLOGITUDE(String LOGITUDE) {
        this.LOGITUDE = LOGITUDE;
    }

    public void setDEVICEDATE(String DEVICEDATE) {
        this.DEVICEDATE = DEVICEDATE;
    }

    public void setDEVICETYPE(String DEVICETYPE) {
        this.DEVICETYPE = DEVICETYPE;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public void setTERMINALID(String TERMINALID) {
        this.TERMINALID = TERMINALID;
    }

    public void setSTOREID(String STOREID) {
        this.STOREID = STOREID;
    }

    public void setFCMKEY(String FCMKEY) {
        this.FCMKEY = FCMKEY;
    }

    public void setMACID(String MACID) {
        this.MACID = MACID;
    }
}
