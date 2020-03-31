package com.apollopharmacy.mpospharmacist.ui.additem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class PaymentMethodModel extends BaseObservable {

    private boolean cashMode = true;
    private boolean cardMode = false;
    private boolean oneApolloMode = false;
    private boolean hellingCardMode = false;
    private boolean isAdditionalDisc = false;
    private boolean isBalanceAmount = false;
    private boolean isOTPView = false;
    private double balanceAmount;
    private boolean generateBill = false;
    private boolean isPaymentDone = false;
    private SaveRetailsTransactionRes saveRetailsTransactionRes;
    private boolean isLoadApolloPoints = false;
    private boolean isErrorApolloPoints = false;
    private boolean isPaymentInitiate = false;

    @Bindable
    public boolean isPaymentInitiate() {
        return isPaymentInitiate;
    }

    public void setPaymentInitiate(boolean paymentInitiate) {
        isPaymentInitiate = paymentInitiate;
        notifyPropertyChanged(BR.paymentInitiate);
    }

    @Bindable
    public boolean isLoadApolloPoints() {
        return isLoadApolloPoints;
    }

    public void setLoadApolloPoints(boolean loadApolloPoints) {
        isLoadApolloPoints = loadApolloPoints;
        notifyPropertyChanged(BR.loadApolloPoints);
    }
    @Bindable
    public boolean isErrorApolloPoints() {
        return isErrorApolloPoints;
    }

    public void setErrorApolloPoints(boolean errorApolloPoints) {
        isErrorApolloPoints = errorApolloPoints;
        notifyPropertyChanged(BR.errorApolloPoints);
    }

    @Bindable
    public boolean isCashMode() {
        return cashMode;
    }

    public void setCashMode(boolean cashMode) {
        this.cashMode = cashMode;
        notifyPropertyChanged(BR.cashMode);
    }

    @Bindable
    public boolean isCardMode() {
        return cardMode;
    }

    public void setCardMode(boolean cardMode) {
        this.cardMode = cardMode;
        notifyPropertyChanged(BR.cardMode);
    }

    @Bindable
    public boolean isOneApolloMode() {
        return oneApolloMode;
    }

    public void setOneApolloMode(boolean oneApolloMode) {
        this.oneApolloMode = oneApolloMode;
        notifyPropertyChanged(BR.oneApolloMode);
    }

    @Bindable
    public boolean isHellingCardMode() {
        return hellingCardMode;
    }

    public void setHellingCardMode(boolean hellingCardMode) {
        this.hellingCardMode = hellingCardMode;
        notifyPropertyChanged(BR.hellingCardMode);
    }

    @Bindable
    public boolean isAdditionalDisc() {
        return isAdditionalDisc;
    }

    public void setAdditionalDisc(boolean additionalDisc) {
        isAdditionalDisc = additionalDisc;
        notifyPropertyChanged(BR.additionalDisc);
    }

    @Bindable
    public boolean isBalanceAmount() {
        return isBalanceAmount;
    }

    public void setBalanceAmount(boolean balanceAmount) {
        isBalanceAmount = balanceAmount;
        notifyPropertyChanged(BR.balanceAmount);
    }

    @Bindable
    public boolean isOTPView() {
        return isOTPView;
    }

    public void setOTPView(boolean oTPView) {
        isOTPView = oTPView;
        notifyPropertyChanged(BR.oTPView);
    }
    @Bindable
    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
        notifyPropertyChanged(BR.balanceAmount);
    }

    @Bindable
    public boolean isGenerateBill() {
        return generateBill;
    }

    public void setGenerateBill(boolean generateBill) {
        this.generateBill = generateBill;
        notifyPropertyChanged(BR.generateBill);
    }

    public SaveRetailsTransactionRes getSaveRetailsTransactionRes() {
        return saveRetailsTransactionRes;
    }

    public void setSaveRetailsTransactionRes(SaveRetailsTransactionRes saveRetailsTransactionRes) {
        this.saveRetailsTransactionRes = saveRetailsTransactionRes;
    }

    @Bindable
    public boolean isPaymentDone() {
        return isPaymentDone;
    }

    public void setPaymentDone(boolean paymentDone) {
        isPaymentDone = paymentDone;
        notifyPropertyChanged(BR.paymentDone);
    }
}
