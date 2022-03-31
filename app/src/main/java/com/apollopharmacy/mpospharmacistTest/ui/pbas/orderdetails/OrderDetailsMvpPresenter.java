package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface OrderDetailsMvpPresenter<V extends OrderDetailsMvpView> extends MvpPresenter<V> {

    void onSelectContinue();

    void onClickBackIcon();
}
