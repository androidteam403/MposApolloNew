package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface StockInwardProcessMvpPresenter<V extends StockInwardProcessMvpView> extends MvpPresenter<V> {

    void onClickFromDate();

    void onClickToDate();
}
