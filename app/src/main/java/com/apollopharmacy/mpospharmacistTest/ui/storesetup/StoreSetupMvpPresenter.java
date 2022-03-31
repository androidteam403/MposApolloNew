package com.apollopharmacy.mpospharmacistTest.ui.storesetup;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface StoreSetupMvpPresenter<V extends StoreSetupMvpView> extends MvpPresenter<V> {

    void onSelectStoreSearch();

    void onCancelBtnClick();

    void handleStoreSetupService();

    void getStoreList();

    void insertAdminLoginDetails();

    void checkConfingApi();

    void onVerifyClick();


}
