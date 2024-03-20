package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface AllOrdersMvpPresenter<V extends AllOrdersMvpView> extends MvpPresenter<V> {
    void onClickBack();

    void getOmsTransactionHeader();
}
