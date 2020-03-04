package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface CustomerMasterMvpView extends MvpView {

    void onDateClick();

    void onSubmitClick();

    void onAnniversaryClick();

    void onRegistrationClick();

    void onClickBackPressed();

    void addCustomerSuccess(AddCustomerResModel addCustomerResModel);

    void addCustomerFailed(String errMsg);

    String getFirstName();

    String getMiddleName();

    String getLastName();

    int getAge();

    String getGenderOption();

    String getDOB();

    String getPostalAddress();

    String getCityOption();

    String getStateOption();

    String getDistrictOption();

    String getZipCode();

    String getEmail();

    String getTelephone();

    String getMobile();

    String getAnniversary();

    String getMaritalStatus();

    int getNumberOfDependants();

    String getCardNumber();

    String getDateOfReg();
}
