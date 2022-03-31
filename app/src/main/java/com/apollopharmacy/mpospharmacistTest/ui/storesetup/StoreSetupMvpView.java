package com.apollopharmacy.mpospharmacistTest.ui.storesetup;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;

public interface StoreSetupMvpView extends MvpView {

    void onSelectStoreSearch();

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
