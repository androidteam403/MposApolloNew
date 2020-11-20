package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ADSPlayListResponse {
    @Expose
    @SerializedName("zcServerHost")
    private String zcServerHost;
    @Expose
    @SerializedName("zcServerIp")
    private String zcServerIp;
    @Expose
    @SerializedName("zcServerDateTime")
    private String zcServerDateTime;
    @Expose
    @SerializedName("data")
    private DataEntity data;
    @Expose
    @SerializedName("success")
    private boolean success;

    public String getZcServerHost() {
        return zcServerHost;
    }

    public String getZcServerIp() {
        return zcServerIp;
    }

    public String getZcServerDateTime() {
        return zcServerDateTime;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }
}
