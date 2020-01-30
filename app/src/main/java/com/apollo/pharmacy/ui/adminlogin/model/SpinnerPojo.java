package com.apollo.pharmacy.ui.adminlogin.model;

public class SpinnerPojo {
    private String loginType;

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
