package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface SearchCustomerDoctorDetailsMvpView extends MvpView {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onBackPressedClick();
}
