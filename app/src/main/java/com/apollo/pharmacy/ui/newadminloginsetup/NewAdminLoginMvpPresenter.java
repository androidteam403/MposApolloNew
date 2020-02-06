package com.apollo.pharmacy.ui.newadminloginsetup;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface NewAdminLoginMvpPresenter<V extends NewAdminLoginMvpView> extends MvpPresenter<V> {

    void onLoginClick();
}
