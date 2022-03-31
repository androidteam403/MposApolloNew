package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;

public interface CustomerMasterMvpView extends MvpView {

    void onDateClick();

    void onSubmitClick();

    //void onAnniversaryClick();

    void onClickBackPressed();

    void addCustomerSuccess(AddCustomerResModel addCustomerResModel);

    void generateotpSuccess(ModelMobileNumVerify response,String otp);

    void generateotpFailed(String errMsg);

    void addCustomerFailed(String errMsg);

    String getFirstName();

   // String getMiddleName();

  //  String getLastName();

   // int getAge();

    String getGenderOption();

    String getDOB();

    String getPostalAddress();

   // String getCityOption();

   // String getStateOption();

   // String getDistrictOption();

    String getZipCode();

  //  String getEmail();

  //  String getTelephone();

    String getMobile();

   // String getAnniversary();

   // String getMaritalStatus();

  //  int getNumberOfDependants();

    String getCardNumber();

    String getDateOfReg();

    void onSucessPlayList();

    Context getContext();

    void showOTPPopUp(String otp);

    //void sendSmsservice();
}
