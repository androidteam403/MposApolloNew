package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.selfidscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface ShelfIdScannerMvpPresenter<V extends ShelfIdScannerMvpView> extends MvpPresenter<V> {
    void onClickClose();
}
