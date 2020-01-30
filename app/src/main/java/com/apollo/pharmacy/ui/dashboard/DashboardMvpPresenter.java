package com.apollo.pharmacy.ui.dashboard;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface DashboardMvpPresenter<V extends DashboardMvpView> extends MvpPresenter<V> {

    void onSearchProductClick();
}
