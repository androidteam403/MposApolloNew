package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginResModel;

public interface NewAdminLoginMvpView extends MvpView {


    String getUserID();

    String getPassword();

    void userLoginSuccess(AdminLoginResModel loginResModel);

    void userLoginFailed(String errMsg);

    boolean validation();
}
