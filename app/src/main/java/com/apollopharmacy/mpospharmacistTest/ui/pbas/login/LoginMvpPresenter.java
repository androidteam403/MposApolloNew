package com.apollopharmacy.mpospharmacistTest.ui.pbas.login;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onLoginClick();
}
