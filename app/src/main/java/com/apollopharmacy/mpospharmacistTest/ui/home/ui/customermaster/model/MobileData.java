package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileData {
    @SerializedName("group_id")
    @Expose
    private Long groupId;
    @SerializedName("0")
    @Expose
    private MobileResult mobileResult;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public MobileResult getMobileResult() {
        return mobileResult;
    }

    public void setMobileResult(MobileResult mobileResult) {
        this.mobileResult = mobileResult;
    }
}
