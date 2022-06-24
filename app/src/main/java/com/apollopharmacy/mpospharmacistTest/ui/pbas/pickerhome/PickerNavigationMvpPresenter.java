package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface PickerNavigationMvpPresenter<V extends PickerNavigationMvpView> extends MvpPresenter<V> {
    String getLoginUserName();
    void onGetOmsTransaction(String fulfilmentId, boolean isItemClick);
    String getLoinStoreLocation();
    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();
    void logoutUser();
}
