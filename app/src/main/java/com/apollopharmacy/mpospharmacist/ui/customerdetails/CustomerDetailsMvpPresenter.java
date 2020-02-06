package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface CustomerDetailsMvpPresenter <V extends CustomerDetailsMvpView> extends MvpPresenter<V> {
    void onNewCustomer();
}
