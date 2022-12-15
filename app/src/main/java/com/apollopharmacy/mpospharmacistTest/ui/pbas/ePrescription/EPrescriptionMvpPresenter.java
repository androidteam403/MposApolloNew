package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface EPrescriptionMvpPresenter<V extends EPrescriptionMvpView> extends MvpPresenter<V> {

    void fetchFulfilmentOrderList();

    String getLoginUserName();

    String getLoinStoreLocation();

    String getTerminalId();

    String getSiteId();

}
