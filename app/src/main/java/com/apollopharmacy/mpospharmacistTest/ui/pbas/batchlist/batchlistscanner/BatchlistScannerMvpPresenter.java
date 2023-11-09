package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface BatchlistScannerMvpPresenter<V extends BatchlistScannerMvpView> extends MvpPresenter<V> {
    void onClickClose();

    void onClickSkip();

    void getBatchDetailsByBarCode(String s, String itemId);

    void onClickReferToAdmin();

    void onClickOnHold();
}
