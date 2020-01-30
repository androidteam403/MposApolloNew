package com.apollo.pharmacy.ui.splash;

import com.apollo.pharmacy.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openAdminSetupActivity();

    void openMainActivity();

    void startSyncService();
}
