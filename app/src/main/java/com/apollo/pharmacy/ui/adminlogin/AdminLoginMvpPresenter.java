package com.apollo.pharmacy.ui.adminlogin;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface AdminLoginMvpPresenter<V extends AdminLoginMvpView> extends MvpPresenter<V> {

    void onLoginClick();

    void onSetUpClick();

    void insertAdminLoginDetails();
}
