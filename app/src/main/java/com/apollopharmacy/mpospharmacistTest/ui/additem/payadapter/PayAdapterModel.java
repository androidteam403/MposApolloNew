package com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter;

public class PayAdapterModel {
    private String amount;
    private String paymentType;
    private double value;
    private boolean amountVoid;
    private int crossDis = 0;
    private int strikeoff = 0;
    private boolean responseChecking;
    private int strikeThroughText = 0;

    public int getStrikeThroughText() {
        return strikeThroughText;
    }

    public void setStrikeThroughText(int strikeThroughText) {
        this.strikeThroughText = strikeThroughText;
    }

    public boolean isResponseChecking() {
        return responseChecking;
    }

    public void setResponseChecking(boolean responseChecking) {
        this.responseChecking = responseChecking;
    }

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
