package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;


import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
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

    void LoadOmsOrderSuccess(CustomerDataResBean response);

    void LoadOmsOrderFailure(CustomerDataResBean response);


    void CheckBatchStock(GetOMSTransactionResponse response);

    void onBatchStockFailure(GetOMSTransactionResponse response);

    void onplusOrderDetails();

    void onSuccessBatchInfo(GetBatchInfoRes response);

    void onFailedBatchInfo(GetBatchInfoRes body);


    void onminusVendorDetails();

    void onPlusVendorDetails();

    void onActionsContinue();

    void onGenerateBill();

    void onPrintLabel();

    void onPrintShippingLabel();

    void onSendBacktoPackerLabel();

    //
    void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response);

    void OmsOrderUpdateFailure(OMSOrderUpdateResponse response);



}
