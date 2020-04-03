package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface PharmacistLoginMvpPresenter<V extends PharmacistLoginMvpView> extends MvpPresenter<V> {

    void onClickLogin();

    void onClickCampaignClose();

    void onInstoreCLick();

    void onSelectCampaign();

    void getUserId();

    void getCampaign();

    void userLoginInStoreApi();

    void userLoginCampaignApi();

    void getGlobalConfigration();
}
