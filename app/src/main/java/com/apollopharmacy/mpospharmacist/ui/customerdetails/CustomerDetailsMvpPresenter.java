package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

public interface CustomerDetailsMvpPresenter<V extends CustomerDetailsMvpView> extends MvpPresenter<V> {
    void onAddCustomerClick();

    void onActionBarBackPressed();

    void onCustomerSearchClick();

    void onClickSelectBtn(GetCustomerResponse.CustomerEntity customerEntity);
}
