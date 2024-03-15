package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface AdminMvpPresenter<V extends AdminMvpView> extends MvpPresenter<V> {
    void getOmsTransactionHeader();

    void onClickAllOrders();
}
