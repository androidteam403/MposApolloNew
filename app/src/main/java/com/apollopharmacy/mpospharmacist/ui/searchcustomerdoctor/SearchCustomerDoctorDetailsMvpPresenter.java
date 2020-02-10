package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface SearchCustomerDoctorDetailsMvpPresenter<V extends SearchCustomerDoctorDetailsMvpView> extends MvpPresenter<V> {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onActionBarBackPress();
}
