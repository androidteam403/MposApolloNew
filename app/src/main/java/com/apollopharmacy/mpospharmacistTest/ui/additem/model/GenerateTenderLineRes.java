package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateTenderLineRes {
    @Expose
    @SerializedName("GenerateTenderLineResult")
    private CalculatePosTransactionRes GenerateTenderLineResult;

    @Expose
    @SerializedName("ValidateOMSOrderResult")
    private CalculatePosTransactionRes ValidateOMSOrderResult;

    public CalculatePosTransactionRes getGenerateTenderLineResult() {
        return GenerateTenderLineResult;
    }
    public CalculatePosTransactionRes getValidateOMSOrderResult() {
        return ValidateOMSOrderResult;
    }

}
