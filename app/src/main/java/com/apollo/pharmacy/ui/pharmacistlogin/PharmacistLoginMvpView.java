package com.apollo.pharmacy.ui.pharmacistlogin;


import com.apollo.pharmacy.ui.base.MvpView;

import okhttp3.ResponseBody;

public interface PharmacistLoginMvpView extends MvpView {
    void onLoginSuccess(ResponseBody response);

    void onSuccessLogin();

    void onIntentCall();
}
