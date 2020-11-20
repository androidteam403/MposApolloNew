package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEntity {
    @Expose
    @SerializedName("listData")
    private ListDataEntity listData;

    @Expose
    @SerializedName("zcDebugLogs")
    private ZcDebugLogs zcDebugLogs;

    public ListDataEntity getListData() {
        return listData;
    }

    public void setListData(ListDataEntity listData) {
        this.listData = listData;
    }
}
