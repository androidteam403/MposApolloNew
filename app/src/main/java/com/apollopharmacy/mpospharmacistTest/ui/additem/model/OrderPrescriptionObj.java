package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPrescriptionObj {
    @Expose
    @SerializedName("CATEGORYCODE")
    private String CATEGORYCODE;
    @Expose
    @SerializedName("PERSCRIPTIONURL")
    private String PERSCRIPTIONURL;
    @Expose
    @SerializedName("PRESCRIPTIONNO")
    private String PRESCRIPTIONNO;
    @Expose
    @SerializedName("Type")
    private String Type;

    public String getCATEGORYCODE() {
        return CATEGORYCODE;
    }

    public String getPERSCRIPTIONURL() {
        return PERSCRIPTIONURL;
    }

    public String getPRESCRIPTIONNO() {
        return PRESCRIPTIONNO;
    }

    public String getType() {
        return Type;
    }

}
