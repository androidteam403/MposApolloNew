package com.apollopharmacy.mpospharmacist.ui.adminlogin;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AdminLoginMvpPresenter<V extends AdminLoginMvpView> extends MvpPresenter<V> {

    void onLoginClick();

    void onSetUpClick();

    void insertAdminLoginDetails();
}
