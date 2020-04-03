package com.apollopharmacy.mpospharmacist.ui.splash;

import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openAdminSetupActivity();

    void openMainActivity();

    void storeSetupActivity();

    void startSyncService();

    void updateTenderType(GetTenderTypeRes data);
}
