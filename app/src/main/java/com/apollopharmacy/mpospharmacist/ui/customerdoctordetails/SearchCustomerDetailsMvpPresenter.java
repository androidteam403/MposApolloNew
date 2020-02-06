package com.apollopharmacy.mpospharmacist.ui.customerdoctordetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface SearchCustomerDetailsMvpPresenter<V extends SearchCustomerDetailsMvpView> extends MvpPresenter<V> {

    void onPhoneClick();

    void onRegisterClick();
}
