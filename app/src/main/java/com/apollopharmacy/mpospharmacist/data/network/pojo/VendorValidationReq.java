package com.apollopharmacy.mpospharmacist.data.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class VendorValidationReq {

    @Expose
    @SerializedName("DEVICEID")
    private String DEVICEID;
    @Expose
    @SerializedName("KEY")
    private String KEY;

    public String getDEVICEID() {
        return DEVICEID;
    }

    public String getKEY() {
        return KEY;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
}