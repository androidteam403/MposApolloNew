package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;

import okhttp3.ResponseBody;

public interface PharmacistLoginMvpView extends MvpView {

    void onClickLogin();

    void onClickInstore();

    void onCampaignSelect();

    void getUserIds(UserModel body);

    void userLoginSuccess(LoginResModel loginResModel);

    void userLoginFailed();

    String getUserId();

    String getCampaignId();

    String getStoreId();

    String getTerminalId();

    String getUserPassword();
}
