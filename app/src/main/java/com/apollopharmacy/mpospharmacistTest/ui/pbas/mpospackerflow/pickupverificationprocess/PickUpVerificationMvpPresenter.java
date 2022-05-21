package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;

public interface PickUpVerificationMvpPresenter<V extends PickUpVerificationMvpView> extends MvpPresenter<V> {
    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();

    void fetchOMSCustomerInfo(String refNumber);

    void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest);

    void onClickVerification();
}
