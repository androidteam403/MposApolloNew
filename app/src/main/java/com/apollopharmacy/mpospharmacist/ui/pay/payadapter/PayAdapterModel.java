package com.apollopharmacy.mpospharmacist.ui.pay.payadapter;

public class PayAdapterModel {
    private String amount;

    public PayAdapterModel(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
