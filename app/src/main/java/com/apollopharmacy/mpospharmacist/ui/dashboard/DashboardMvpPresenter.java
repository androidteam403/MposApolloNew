package com.apollopharmacy.mpospharmacist.ui.dashboard;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface DashboardMvpPresenter<V extends DashboardMvpView> extends MvpPresenter<V> {

    void onSearchUserClick();

}
