package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customid")
    @Expose
    private String customid;
    @SerializedName("customid1")
    @Expose
    private String customid1;
    @SerializedName("customid2")
    @Expose
    private String customid2;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomid() {
        return customid;
    }

    public void setCustomid(String customid) {
        this.customid = customid;
    }

    public String getCustomid1() {
        return customid1;
    }

    public void setCustomid1(String customid1) {
        this.customid1 = customid1;
    }

    public String getCustomid2() {
        return customid2;
    }

    public void setCustomid2(String customid2) {
        this.customid2 = customid2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
