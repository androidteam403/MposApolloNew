package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AddItemMvpPresenter<V extends AddItemMvpView> extends MvpPresenter<V> {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickBackPressed();

    void onClickClearAllBtn();

    void onPayButtonClick();

    void onClickCardPayment();

    void onClickCashPayment();

    void onClickCardPaymentPay();

    void onClickCashPaymentPay();

    void onClickEditItemsList();

    void calculatePosTransaction();
}
