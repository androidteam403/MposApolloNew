package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model;

public class OrderReturnModel {
    private String paymentType;
    private double paidAmount;

    public OrderReturnModel(String paymentType, double paidAmount) {
        this.paymentType = paymentType;
        this.paidAmount = paidAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }
}
