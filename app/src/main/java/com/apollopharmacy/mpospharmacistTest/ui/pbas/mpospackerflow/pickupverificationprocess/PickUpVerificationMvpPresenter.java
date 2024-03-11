package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface PickUpVerificationMvpPresenter<V extends PickUpVerificationMvpView> extends MvpPresenter<V> {
    void onPartialWarningYesClick();

    void onPartialWarningNoClick();
    void fetchFulfilmentOrderList();

    void onClickReVerificatio();
    void getTransactionID();
    void fetchOMSCustomerInfoBiller(String refNumber);

    void fetchOMSCustomerInfo(String refNumber);
    void getDoctorsList();
    void getCorporateList();

    void onClickVerification();

    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader);

    void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest);

    void onClickTakePrint();

    void getBatchDetailsApi(String itemId);
    void onCheckBatchStock(CustomerDataResBean customerDataResBean);
    void onLoadOmsOrder(CustomerDataResBean customerDataResBean);

    void onClickPackerStatusUpdate();

    GetGlobalConfingRes getGlobalConfigRes();
}
