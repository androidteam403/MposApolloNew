package com.apollopharmacy.mpospharmacist.ui.additem.model;

public class OrderPriceInfoModel {

    public String DiscType = "---";
    public double PharmaTotalAmount;
    public double FmcgTotalAmount;
    public double PlTotalAmount;
    public double RoundedAmount;
    public double MrpTotalAmount;
    public double TaxableTotalAmount;
    public double DiscTotalAmount;
    public double OrderTotalAmount;
    public double OrderSavingsAmount;
    public double OrderSavingsPercentage;
    public double taxAmount;

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getDiscType() {
        return DiscType;
    }

    public void setDiscType(String discType) {
        DiscType = discType;
    }

    public double getPharmaTotalAmount() {
        return PharmaTotalAmount;
    }

    public void setPharmaTotalAmount(double pharmaTotalAmount) {
        PharmaTotalAmount = pharmaTotalAmount;
    }

    public double getFmcgTotalAmount() {
        return FmcgTotalAmount;
    }

    public void setFmcgTotalAmount(double fmcgTotalAmount) {
        FmcgTotalAmount = fmcgTotalAmount;
    }

    public double getPlTotalAmount() {
        return PlTotalAmount;
    }

    public void setPlTotalAmount(double plTotalAmount) {
        PlTotalAmount = plTotalAmount;
    }

    public double getRoundedAmount() {
        return RoundedAmount;
    }

    public void setRoundedAmount(double roundedAmount) {
        RoundedAmount = roundedAmount;
    }

    public double getMrpTotalAmount() {
        return MrpTotalAmount;
    }

    public void setMrpTotalAmount(double mrpTotalAmount) {
        MrpTotalAmount = mrpTotalAmount;
    }

    public double getTaxableTotalAmount() {
        return TaxableTotalAmount;
    }

    public void setTaxableTotalAmount(double taxableTotalAmount) {
        TaxableTotalAmount = taxableTotalAmount;
    }

    public double getDiscTotalAmount() {
        return DiscTotalAmount;
    }

    public void setDiscTotalAmount(double discTotalAmount) {
        DiscTotalAmount = discTotalAmount;
    }

    public double getOrderTotalAmount() {
        return OrderTotalAmount;
    }

    public void setOrderTotalAmount(double orderTotalAmount) {
        OrderTotalAmount = orderTotalAmount;
    }

    public double getOrderSavingsAmount() {
        return OrderSavingsAmount;
    }

    public void setOrderSavingsAmount(double orderSavingsAmount) {
        OrderSavingsAmount = orderSavingsAmount;
    }

    public double getOrderSavingsPercentage() {
        return OrderSavingsPercentage;
    }

    public void setOrderSavingsPercentage(double orderSavingsPercentage) {
        OrderSavingsPercentage = orderSavingsPercentage;
    }
}
