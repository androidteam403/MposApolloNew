package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

public interface CustomerDetailsMvpView extends MvpView {
    void onAddCustomerClick();

    void onClickBackPressed();

    void onVoiceSearchClick();

    String getCustomerNumber();

    void setCustomerErrorMessage();

    void onSuccessCustomerSearch(GetCustomerResponse body);

    void onFailedCustomerSearch();

    void onSubmitBtnClick(GetCustomerResponse.CustomerEntity customerEntity);

    void onEditBtnClick(GetCustomerResponse.CustomerEntity customerEntity);

    void onSucessPlayList();

    Context getContext();
}
