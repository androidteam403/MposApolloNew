package com.apollo.pharmacy.ui.pharmacistlogin;


import com.apollo.pharmacy.ui.base.MvpView;

import okhttp3.ResponseBody;

public interface PharmacistLoginMvpView extends MvpView {
    void onLoginSuccess(ResponseBody response);

    String getMobile();

    String getOtp();

    void showInputMobileError(String errorMessage);

    void showInputOtpError(String errorMessage);

    void onSuccessSendOtp();

    void onSuccessLogin();

    void onIntentCall();
}
