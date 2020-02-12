package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;

public interface SearchCustomerDoctorDetailsMvpPresenter<V extends SearchCustomerDoctorDetailsMvpView> extends MvpPresenter<V> {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onActionBarBackPress();

    void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity);

    void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity);

    void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity);
}
