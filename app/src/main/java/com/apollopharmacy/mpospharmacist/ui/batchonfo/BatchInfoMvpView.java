package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface BatchInfoMvpView extends MvpView {
    void onIncrementClick();

    void onDecrementClick();

    void onNavigateNextActivity();
}
