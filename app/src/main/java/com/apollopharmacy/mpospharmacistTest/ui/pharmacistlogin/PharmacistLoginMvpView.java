package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin;


import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UpdatePatchResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

public interface PharmacistLoginMvpView extends MvpView {

    void onClickLogin();

    void onClickInstore();

    void onClickCampaignClose();

    void getUserIds(UserModel body);

    void setCampaignDetails(CampaignDetailsRes campaignDetails);

    void userLoginSuccess();

    void userLoginFailed(String errMsg);

    String getUserId();

    String getUserPassword();

    Context getContext();

    void onSucessMposPosiflex();

    void onSuccessUpdatePatchApiCAll(UpdatePatchResponse updatePatchResponse);
}
