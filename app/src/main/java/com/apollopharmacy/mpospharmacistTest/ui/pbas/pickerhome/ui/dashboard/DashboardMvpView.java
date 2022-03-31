package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface DashboardMvpView extends MvpView {
    void onClickOpenOrders();

    void onClickFromDate();

    void onClickToDate();

    void onClickToday();

    void onClickWeekly();

    void onClickMonthly();

    void onClickYearly();
}
