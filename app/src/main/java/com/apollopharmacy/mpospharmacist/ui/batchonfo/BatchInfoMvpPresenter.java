package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface BatchInfoMvpPresenter<V extends BatchInfoMvpView> extends MvpPresenter<V> {
    void onClickIncrement();

    void onClickDecrement();

    void onNavigateNextActivity();

    void onActionBarBackPressed();
}
