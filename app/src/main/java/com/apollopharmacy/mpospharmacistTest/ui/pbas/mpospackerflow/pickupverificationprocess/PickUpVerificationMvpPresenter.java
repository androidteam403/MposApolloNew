package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;

public interface PickUpVerificationMvpPresenter<V extends PickUpVerificationMvpView> extends MvpPresenter<V> {
    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();

    void fetchOMSCustomerInfo(String refNumber);

    void onClickVerification();

    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader);

    void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest);

    void onClickTakePrint();

    void getBatchDetailsApi(String itemId);

    void onClickPackerStatusUpdate();
}
