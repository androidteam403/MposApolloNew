package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.Uhid_details;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

public interface BillingMvpView extends MvpView {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onBackPressedClick();

    void customerEditClick(GetCustomerResponse.CustomerEntity customerEntity);

    void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity);

    void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity);

    void onContinueBtnClick();

    void showTransactionID(TransactionIDResModel model);

    void getCorporateList(CorporateModel corporateModel);

    void getDoctorSearchList(DoctorSearchResModel model);

    void getSalesOriginList(SalesOriginResModel model);

    void getSalesListDropDown(SalesOriginResModel model);

    String getPrgTracing();

    void onUploadApiCall();

    void onFaliureStaffListData();

    void onSucessStaffListData(PharmacyStaffApiRes res);

    void onSucessPlayList();

    Context getContext();

    void updateUHIDDetails(GetHBPUHIDDetailsResponse getHBPUHIDDetailsResponse);

}
