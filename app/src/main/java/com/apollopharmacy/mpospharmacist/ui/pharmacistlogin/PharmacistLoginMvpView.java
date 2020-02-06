package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;

import okhttp3.ResponseBody;

public interface PharmacistLoginMvpView extends MvpView {
    void onLoginSuccess(ResponseBody response);

    void onSuccessLogin();

    void onClickLogin();

    void onIntentCall();

    void onClickInstore();

    void onCampaignSelect();

    void getUserIds(UserModel body);
}
