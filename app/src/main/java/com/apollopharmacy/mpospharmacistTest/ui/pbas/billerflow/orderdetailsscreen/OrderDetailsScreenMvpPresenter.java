package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;

public interface OrderDetailsScreenMvpPresenter<V extends OrderDetailsScreenMvpView> extends MvpPresenter<V> {

    void onMinusCustomerDetails();

    void onPlusCustomerDetails();

    void onminusOrderDetails();

    void getTransactionID();

    void getCorporateList();

    void onLoadOmsOrder(CustomerDataResBean customerDataResBean);

    void onCheckBatchStock(CustomerDataResBean customerDataResBean);

    void onCheckStock(GetOMSTransactionResponse response);

    void onplusOrderDetails();

    void onminusVendorDetails();

    void fetchOMSCustomerInfo(String refNumber);

    void onPlusVendorDetails();

    void getBatchDetailsApi(String itemId);

    void onActionsContinue();

    void onGenerateBill();

    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader);

    void onPrintLabel();

    void onPrintShippingLabel();

    void onSendBacktoPackerLabel();

    void onClickProceedAction();

    void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest);

    void onClickSendtoPacker();

    void onClickContinueBill();

}
