package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginReqModel {


    @Expose
    @SerializedName("TerminalID")
    private String TerminalID;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("Password")
    private String Password;
    @Expose
    @SerializedName("UserID")
    private String UserID;

    public void setTerminalID(String terminalID) {
        TerminalID = terminalID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
