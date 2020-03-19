package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateTenderLineRes {


    @Expose
    @SerializedName("GenerateTenderLineResult")
    private CalculatePosTransactionRes GenerateTenderLineResult;

    public CalculatePosTransactionRes getGenerateTenderLineResult() {
        return GenerateTenderLineResult;
    }

}
