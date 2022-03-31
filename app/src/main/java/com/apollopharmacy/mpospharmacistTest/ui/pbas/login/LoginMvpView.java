package com.apollopharmacy.mpospharmacistTest.ui.pbas.login;


import com.apollopharmacy.mpospharmacistTest.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void onLoginSuccess(UserProfile mUser);

    String getEmail();

    String getPassword();

    void showInputError();

    void setPassword(String userId);

    void setEmail(String password);
}
