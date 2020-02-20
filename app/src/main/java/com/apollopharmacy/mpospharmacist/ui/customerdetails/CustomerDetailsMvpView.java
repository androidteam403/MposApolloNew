package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

public interface CustomerDetailsMvpView extends MvpView {
    void onAddCustomerClick();

    void onClickBackPressed();

    String getCustomerNumber();

    void setCustomerErrorMessage();

    void onSuccessCustomerSearch(GetCustomerResponse body);

    void onFailedCustomerSearch();

    void onSubmitBtnClick(GetCustomerResponse.CustomerEntity customerEntity);
}
