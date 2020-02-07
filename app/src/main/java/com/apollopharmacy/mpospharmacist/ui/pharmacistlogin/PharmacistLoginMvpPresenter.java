package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginReqModel;

public interface PharmacistLoginMvpPresenter<V extends PharmacistLoginMvpView> extends MvpPresenter<V> {

    void onClickLogin();
    void onInstoreCLick();
    void onSelectCampaign();

    void getUserId();

    void getCampaign();

    void userLoginApi();
}
