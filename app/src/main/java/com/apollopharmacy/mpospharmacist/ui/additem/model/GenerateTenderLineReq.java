package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateTenderLineReq {


    @Expose
    @SerializedName("POSTransaction")
    private CalculatePosTransactionRes POSTransaction;
    @Expose
    @SerializedName("Type")
    private TypeEntity Type;
    @Expose
    @SerializedName("Wallet")
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public CalculatePosTransactionRes getPOSTransaction() {
        return POSTransaction;
    }

    public TypeEntity getType() {
        return Type;
    }

    public void setPOSTransaction(CalculatePosTransactionRes POSTransaction) {
        this.POSTransaction = POSTransaction;
    }

    public void setType(TypeEntity type) {
        Type = type;
    }

}
