package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model;

public class OrderReturnModel {
    private String paymentType;
    private String paidAmount;

    public OrderReturnModel(String paymentType, String paidAmount) {
        this.paymentType = paymentType;
        this.paidAmount = paidAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }
}
