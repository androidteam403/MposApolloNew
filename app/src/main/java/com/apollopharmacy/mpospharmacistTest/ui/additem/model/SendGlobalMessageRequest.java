package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class SendGlobalMessageRequest implements Serializable {


    @Expose
    @SerializedName("URL")
    private String URL;
    @Expose
    @SerializedName("MOBILENO")
    private String MOBILENO;
    @Expose
    @SerializedName("SOURCEFOR")
    private String SOURCEFOR;
    @Expose
    @SerializedName("TYPE")
    private String TYPE;

    public String getURL() {
        return URL;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public String getSOURCEFOR() {
        return SOURCEFOR;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }

    public void setSOURCEFOR(String SOURCEFOR) {
        this.SOURCEFOR = SOURCEFOR;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
