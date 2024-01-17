package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.selfidscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface ShelfIdScannerMvpPresenter<V extends ShelfIdScannerMvpView> extends MvpPresenter<V> {
    void onClickClose();

    GetGlobalConfingRes getGlobalConfiguration();
}
