package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface OrderSummaryMvpView extends MvpView {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    void onSucessPlayList();

    Context getContext();
}
