package com.apollopharmacy.mpospharmacist.ui.pay;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface PayMvpPresenter<V extends PayMvpView> extends MvpPresenter<V> {

    void NavigateToHomeScreen();

    void onClickCardPayment();

    void onClickCashPayment();

    void onClickCardPaymentPay();
}
