package com.apollopharmacy.mpospharmacist.ui.storesetup;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface StoreSetupMvpPresenter <V extends StoreSetupMvpView> extends MvpPresenter<V> {

    void onSelectStoreSearch();

    void onSaveBtnClick();

    void onCancelBtnClick();

    void handleStoreSetupService();

    void getStoreList();

    void insertAdminLoginDetails();

    void checkConfingApi();

    void onVerifyClick();

}
