package com.apollo.pharmacy.ui.customerdetails;

import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
import com.apollo.pharmacy.ui.base.MvpPresenter;
import com.apollo.pharmacy.ui.base.MvpView;

public interface CustomerDetailsMvpPresenter <V extends CustomerDetailsMvpView> extends MvpPresenter<V> {
    void onNewCustomer();
}
