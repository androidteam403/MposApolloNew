package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface PickUpVerificationMvpPresenter<V extends PickUpVerificationMvpView> extends MvpPresenter<V> {
    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();

    void fetchOMSCustomerInfo(String refNumber);

    void onClickVerification();

    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader);
}
