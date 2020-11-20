package com.apollopharmacy.mpospharmacist.ui.storesetup;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

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
