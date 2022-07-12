package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.ArrayList;
import java.util.List;

public interface EPrescriptionMedicineDetailsMvpView extends MvpView {

    void onSuccessTransactionList(List<EPrescriptionMedicineResponse> body);

    void onClickDropDown(ArrayList<String> items);

    void onSubstituteSelectedItem(EPrescriptionSubstituteModelResponse.Substitute substitute, int position, EPrescriptionSubstituteModelResponse.Substitute substituteArtCode, List<EPrescriptionSubstituteModelResponse.Substitute> substituteLists);

    void onSuccessSubstituteList(EPrescriptionSubstituteModelResponse body);

    void onFailureSubstituteList(EPrescriptionSubstituteModelResponse body);

    void showTransactionID(TransactionIDResModel body);

    void getCorporateList(CorporateModel body);

    void CheckBatchStockSuccess(CustomerDataResBean body);

    void CheckBatchStockFailure(CustomerDataResBean body);

    void onSuccessOnlineBill(CustomerDataResBean customerDataResBean);

    void onFailureOnlineBill(CustomerDataResBean customerDataResBean);

    void onReqQtyUpdate(EPrescriptionMedicineResponse medicineResponse);

//    void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body);
}

