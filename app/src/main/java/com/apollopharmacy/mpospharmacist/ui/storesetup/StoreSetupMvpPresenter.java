package com.apollopharmacy.mpospharmacist.ui.storesetup;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface StoreSetupMvpPresenter <V extends StoreSetupMvpView> extends MvpPresenter<V> {
    void onClickSelectStore();

}
