package com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model.AdminLoginResModel;

public interface NewAdminLoginMvpView extends MvpView {

    String getUserID();

    String getPassword();

    void userLoginSuccess(AdminLoginResModel loginResModel);

    void userLoginFailed(String errMsg);

    boolean validation();

}
