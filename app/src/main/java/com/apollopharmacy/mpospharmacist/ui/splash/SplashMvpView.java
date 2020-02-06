package com.apollopharmacy.mpospharmacist.ui.splash;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openAdminSetupActivity();

    void openMainActivity();

    void startSyncService();
}
