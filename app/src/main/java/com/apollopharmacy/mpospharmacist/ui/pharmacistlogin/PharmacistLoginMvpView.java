package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;

public interface PharmacistLoginMvpView extends MvpView {

    void onClickLogin();

    void onClickInstore();

    void onClickCampaignClose();

    void onCampaignSelect();

    void getUserIds(UserModel body);

    void setCampaignDetails(CampaignDetailsRes campaignDetails);

    void userLoginSuccess();

    void userLoginFailed(String errMsg);

    String getUserId();

    String getCampaignId();

    String getUserPassword();
}
