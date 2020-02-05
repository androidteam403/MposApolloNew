package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

public class SelectCampaignModel {

    public String loginType;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return loginType;
    }

}
