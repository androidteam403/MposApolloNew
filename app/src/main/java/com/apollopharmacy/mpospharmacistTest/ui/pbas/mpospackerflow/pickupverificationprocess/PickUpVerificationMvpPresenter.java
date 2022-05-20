package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface PickUpVerificationMvpPresenter<V extends PickUpVerificationMvpView> extends MvpPresenter<V> {
    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();
    void fetchOMSMedicineInfo(String refNumber);
    void fetchOMSCustomerInfo(String refNumber);
    void onClickVerification();
}
