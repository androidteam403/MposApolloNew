package com.apollopharmacy.mpospharmacistTest.ui.splash;

import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openAdminSetupActivity();

    void openMainActivity();

    void storeSetupActivity();

    BaseActivity getBaseActivity();

    void startAnimation();

}
