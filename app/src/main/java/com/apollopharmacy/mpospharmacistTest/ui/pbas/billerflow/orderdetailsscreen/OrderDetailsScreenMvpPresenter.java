package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

public interface OrderDetailsScreenMvpPresenter<V extends OrderDetailsScreenMvpView> extends MvpPresenter<V> {

    void onMinusCustomerDetails();

    void onPlusCustomerDetails();

    void onminusOrderDetails();

    void onplusOrderDetails();

    void onminusVendorDetails();
    void fetchOMSCustomerInfo(String refNumber);
    void onPlusVendorDetails();

    void onActionsContinue();

    void onGenerateBill();
    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader);
    void onPrintLabel();

    void onPrintShippingLabel();

    void onSendBacktoPackerLabel();


}
