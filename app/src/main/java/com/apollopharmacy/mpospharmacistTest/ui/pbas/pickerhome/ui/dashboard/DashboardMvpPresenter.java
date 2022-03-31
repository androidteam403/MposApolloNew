package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface DashboardMvpPresenter<V extends DashboardMvpView> extends MvpPresenter<V> {
    void onClickOpenOrders();

    void onClickFromDate();

    void onClickToDate();

    void onClickToday();

    void onClickWeekly();

    void onClickMonthly();

    void onClickYearly();
}
