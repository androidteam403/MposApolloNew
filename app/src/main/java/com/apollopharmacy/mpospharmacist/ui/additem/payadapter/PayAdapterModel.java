package com.apollopharmacy.mpospharmacist.ui.additem.payadapter;

public class PayAdapterModel {
    private String amount;
    private String paymentType;
    private double value;
    private boolean amountVoid;
    private int crossDis=0;
    private int strikeoff=0;

    public int getStrikeoff() {
        return strikeoff;
    }

    public void setStrikeoff(int strikeoff) {
        this.strikeoff = strikeoff;
    }

    public int getCrossDis() {
        return crossDis;
    }

    public void setCrossDis(int crossDis) {
        this.crossDis = crossDis;
    }

    public PayAdapterModel(String paymentType, String amount, double value) {
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

    public boolean isAmountVoid() {
        return amountVoid;
    }

    public void setAmountVoid(boolean amountVoid) {
        this.amountVoid = amountVoid;
    }
}
