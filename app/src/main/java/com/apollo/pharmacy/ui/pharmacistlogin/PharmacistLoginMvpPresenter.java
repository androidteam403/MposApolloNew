package com.apollo.pharmacy.ui.pharmacistlogin;


import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface PharmacistLoginMvpPresenter<V extends PharmacistLoginMvpView> extends MvpPresenter<V> {

    void onClickLogin();
    void onInstoreCLick();
    void onSelectCampaign();
}
