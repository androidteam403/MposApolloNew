package com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

public interface SearchCustomerDoctorDetailsMvpView extends MvpView {

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

    void onSucessPlayList();

    Context getContext();
}
