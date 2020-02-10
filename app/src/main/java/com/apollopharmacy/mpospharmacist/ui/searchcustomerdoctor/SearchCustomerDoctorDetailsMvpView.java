package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

public interface SearchCustomerDoctorDetailsMvpView extends MvpView {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onBackPressedClick();

    void customerEditClick(GetCustomerResponse.CustomerEntity customerEntity);
}
