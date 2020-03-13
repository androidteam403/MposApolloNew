package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface OrdersMvpPresenter<V extends OrdersMvpView> extends MvpPresenter<V> {
    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick();

    void onClickSearchIcon();

    void getOrdersDetails();
}
