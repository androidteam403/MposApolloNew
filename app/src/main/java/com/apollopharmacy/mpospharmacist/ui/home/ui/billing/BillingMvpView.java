package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;

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
}
