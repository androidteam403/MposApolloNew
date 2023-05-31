package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetHBPUHIDDetailsRequest implements Serializable {

    @SerializedName("ClusterCode")
    private String ClusterCode;
    @SerializedName("SiteId")
    private String SiteId;
    @SerializedName("UHID")
    private String UHID;
    @SerializedName("URL")
    private String URL;

    public void setClusterCode(String clusterCode) {
        ClusterCode = clusterCode;
    }

    public void setSiteId(String siteId) {
        SiteId = siteId;
    }

    public void setUHID(String UHID) {
        this.UHID = UHID;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
