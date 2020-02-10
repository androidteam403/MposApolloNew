package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

public interface SearchCustomerDoctorDetailsMvpPresenter<V extends SearchCustomerDoctorDetailsMvpView> extends MvpPresenter<V> {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onActionBarBackPress();

    void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity);
}
