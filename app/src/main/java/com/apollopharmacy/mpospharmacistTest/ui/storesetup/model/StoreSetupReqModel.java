package com.apollopharmacy.mpospharmacistTest.ui.storesetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreSetupReqModel {
    @Expose
    @SerializedName("MACID")
    private String macID;
    @Expose
    @SerializedName("FCMKEY")
    private String fcmKey;
    @Expose
    @SerializedName("STOREID")
    private String storeId;
    @Expose
    @SerializedName("TERMINALID")
    private String terminalId;
    @Expose
    @SerializedName("USERID")
    private String userId;

    public void setMacID(String macID) {
        this.macID = macID;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
