package com.apollopharmacy.mpospharmacist.ui.pay.payadapter;

public class PayAdapterModel {
    private String amount;
    private String paymentType;
    private double value;

    public PayAdapterModel(String paymentType,String amount,double value) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.value = value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getValue() {
        return value;
    }
}
