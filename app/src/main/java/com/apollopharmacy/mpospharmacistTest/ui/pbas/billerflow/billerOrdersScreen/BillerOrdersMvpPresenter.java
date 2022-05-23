package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface BillerOrdersMvpPresenter<V extends BillerOrdersMvpView> extends MvpPresenter<V> {
    void onClickFilterIcon();
    void fetchFulfilmentOrderList();
    void onRackApiCall();
}
