package com.apollopharmacy.mpospharmacist.ui.pay;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface PayMvpView extends MvpView {

    void NavigateToHomeScreen();

    Context getContext();

    String getCardPaymentAmount();

    void setErrorCardPaymentAmountEditText(String message);

    void onClickCardPaymentBtn();

    void onClickCashPaymentBtn();
}
