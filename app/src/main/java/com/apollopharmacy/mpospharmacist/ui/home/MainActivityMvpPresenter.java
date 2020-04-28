package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface MainActivityMvpPresenter<V extends MainActivityMvpView> extends MvpPresenter<V> {
    String getLoginUserName();

    String getLoinStoreLocation();

    void logoutUser();

    void onCheckBuildDetails();

    void getCorporateList();

    void getUnpostedTransaction();

    boolean isKisokMode();

    void setKioskMode(boolean isKioskMode);
}
