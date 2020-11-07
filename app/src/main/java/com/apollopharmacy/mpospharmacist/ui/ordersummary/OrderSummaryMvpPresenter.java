package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface OrderSummaryMvpPresenter<V extends OrderSummaryMvpView> extends MvpPresenter<V> {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void payLoadDataApi();
}
