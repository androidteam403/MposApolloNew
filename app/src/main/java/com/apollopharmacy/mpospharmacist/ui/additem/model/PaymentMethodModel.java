package com.apollopharmacy.mpospharmacist.ui.additem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;

public class PaymentMethodModel extends BaseObservable implements Serializable {

    private boolean cashMode = false;
    private boolean cardMode = false;
    private boolean oneApolloMode = false;
    private boolean walletMode = false;
    private boolean creditMode = false;
    private boolean phonePeMode = false;
    private boolean paytmMode = false;
    private boolean airtelMode = false;
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
    private boolean isEnableCashBtn = false;
    private boolean isEnableCardBtn = false;
    private boolean isEnableApolloBtn = false;
    private boolean isEnableWalletBtn = false;
    private boolean isEnableCreditBtn = false;

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
    public boolean isWalletMode() {
        return walletMode;
    }

    public void setWalletMode(boolean walletMode) {
        this.walletMode = walletMode;
        notifyPropertyChanged(BR.walletMode);
    }

    @Bindable
    public boolean isCreditMode() {
        return creditMode;
    }

    public void setCreditMode(boolean creditMode) {
        this.creditMode = creditMode;
      //  notifyPropertyChanged(BR.creditMode);
    }

    public void setEnableCreditBtn(boolean enableCreditBtn) {
        isEnableCreditBtn = enableCreditBtn;
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

    @Bindable
    public boolean isEnableCashBtn() {
        return isEnableCashBtn;
    }

    @Bindable
    public boolean isEnableCardBtn() {
        return isEnableCardBtn;
    }

    @Bindable
    public boolean isEnableApolloBtn() {
        return isEnableApolloBtn;
    }

    @Bindable
    public boolean isEnableWalletBtn() {
        return isEnableWalletBtn;
    }

    @Bindable
    public boolean isEnableCreditBtn() {
        return isEnableCreditBtn;
    }

    public void setEnableCashBtn(boolean enableCashBtn) {
        isEnableCashBtn = enableCashBtn;
        notifyPropertyChanged(BR.enableCashBtn);
    }

    public void setEnableCardBtn(boolean enableCardBtn) {
        isEnableCardBtn = enableCardBtn;
        notifyPropertyChanged(BR.enableCardBtn);
    }

    public void setEnableApolloBtn(boolean enableApolloBtn) {
        isEnableApolloBtn = enableApolloBtn;
        notifyPropertyChanged(BR.enableApolloBtn);
    }

    public void setEnableWalletBtn(boolean enableWalletBtn) {
        isEnableWalletBtn = enableWalletBtn;
        notifyPropertyChanged(BR.enableWalletBtn);
    }

    public void setEnableCreaditBtn(boolean enableCreditBtn) {
        isEnableCreditBtn = enableCreditBtn;
        notifyPropertyChanged(BR.enableCreditBtn);
    }
    @Bindable
    public boolean isPhonePeMode() {
        return phonePeMode;
    }

    public void setPhonePeMode(boolean phonePeMode) {
        this.phonePeMode = phonePeMode;
        notifyPropertyChanged(BR.phonePeMode);
    }
    @Bindable
    public boolean isPaytmMode() {
        return paytmMode;
    }

    public void setPaytmMode(boolean paytmMode) {
        this.paytmMode = paytmMode;
        notifyPropertyChanged(BR.paytmMode);
    }
    @Bindable
    public boolean isAirtelMode() {
        return airtelMode;
    }

    public void setAirtelMode(boolean airtelMode) {
        this.airtelMode = airtelMode;
        notifyPropertyChanged(BR.airtelMode);
    }
}
