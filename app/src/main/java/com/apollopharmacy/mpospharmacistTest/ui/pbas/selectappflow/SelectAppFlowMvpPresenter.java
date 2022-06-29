package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface SelectAppFlowMvpPresenter<V extends SelectAppFlowMvpView> extends MvpPresenter<V> {

    void onClickContinue();

    void onClickLogout();
}
