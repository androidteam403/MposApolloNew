package com.apollopharmacy.mpospharmacist.ui.storesetup;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;

public interface StoreSetupMvpView extends MvpView {

    void onSelectStoreSearch();

    void onSaveBtnClick();

    void onCancelBtnClick();

    void storeSetupSuccess(DeviceSetupResModel storeResModel);

    void setStoresList(StoreListResponseModel storesList);

    void onSelectStore(StoreListResponseModel.StoreListObj item);

    String getDeviceId();

    String getFcmKey();

    String getStoreId();

    String getStoreContactNum();

    StoreListResponseModel.StoreListObj getStoreDetails();

    String getTerminalId();

    String getUserId();

    String getDeviceType();

    String getRegisteredDate();

    String getLatitude();

    String getLongitude();

    void onNavigateHomeScreen();

    void dialogCloseListiner();

    void onVerifyClick();

    String getEposURL();


}
