package com.apollo.pharmacy.ui.customerdoctordetails;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface SearchCustomerDetailsMvpPresenter<V extends SearchCustomerDetailsMvpView> extends MvpPresenter<V> {

    void onPhoneClick();

    void onRegisterClick();
}
