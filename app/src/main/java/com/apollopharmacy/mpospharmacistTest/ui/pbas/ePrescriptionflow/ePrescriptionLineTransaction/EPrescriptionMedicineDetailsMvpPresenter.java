package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.CheckBatchModelRequest;

public interface EPrescriptionMedicineDetailsMvpPresenter <V extends EPrescriptionMedicineDetailsMvpView & MvpView> extends MvpPresenter<V> {

    void fetchLineTransactionList(String prescriptionNo);

    void fetchSubstituteList(String prescriptionNo);

    void getTransactionID();

    void getCorporateList();

    void omscheckbatchstocks(CustomerDataResBean customerDataResBean);

    String getLoginUserName();

    void onOnlineBillApiCall(CustomerDataResBean customerDataResBean);
}
