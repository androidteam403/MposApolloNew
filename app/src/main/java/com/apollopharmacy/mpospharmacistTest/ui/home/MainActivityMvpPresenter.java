package com.apollopharmacy.mpospharmacistTest.ui.home;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface MainActivityMvpPresenter<V extends MainActivityMvpView> extends MvpPresenter<V> {
    String getLoginUserName();

    String getLoinStoreLocation();

    void logoutUser();


    void getCorporateList();

    void getUnpostedTransaction();

    boolean isKisokMode();

    boolean isOpenScreens();

    void enableopenscreens();

    void disablescreens();

    void getGlobalConfig();

    GetGlobalConfingRes getGlobalConfing();

    String getLastTransactionId();

    void downloadPdf(String transactionId);

}
