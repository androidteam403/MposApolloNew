package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.ArrayList;

public interface OrderDetailsScreenMvpView extends MvpView {

    void onMinusCustomerDetails();

    void onPlusCustomerDetails();

    void showTransactionID(TransactionIDResModel model);

    void getCorporateList(CorporateModel corporateModel);

    void onminusOrderDetails();

    void onSuccessGetOMSTransaction(ArrayList<CustomerDataResBean> response);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void LoadOmsOrderSuccess(CustomerDataResBean response);

    void LoadOmsOrderFailure(CustomerDataResBean response);

    void CheckBatchStockSuccess(CustomerDataResBean response);


    void CheckBatchStockFailure(CustomerDataResBean response);


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

    void onClickProceedAction();

    void OmsOrderUpdateSuccess(OMSOrderForwardResponse response, String mposOrderUpdateRequestType);

    void OmsOrderUpdateFailure(OMSOrderForwardResponse response);

    void onClickSendtoPacker();

    void onClickContinueBill();

    void getDoctorSearchList(DoctorSearchResModel doctorSearchResModel);
}
