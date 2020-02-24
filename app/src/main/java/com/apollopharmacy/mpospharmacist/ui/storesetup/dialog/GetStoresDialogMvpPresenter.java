package com.apollopharmacy.mpospharmacist.ui.storesetup.dialog;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface GetStoresDialogMvpPresenter<V extends GetStoresDialogMvpView> extends MvpPresenter<V> {

    void dismissDialog();
}
