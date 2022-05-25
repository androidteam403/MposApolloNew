package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.List;

public interface OrderDetailsScreenMvpView extends MvpView {

    void onMinusCustomerDetails();

    void onPlusCustomerDetails();
    void showTransactionID(TransactionIDResModel model);
    void getCorporateList(CorporateModel corporateModel);

    void onminusOrderDetails();
    void onSuccessGetOMSTransaction(List<GetOMSTransactionResponse> response);
    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void onplusOrderDetails();

    void onminusVendorDetails();

    void onPlusVendorDetails();

    void onActionsContinue();

    void onGenerateBill();

    void onPrintLabel();

    void onPrintShippingLabel();

    void onSendBacktoPackerLabel();

}
