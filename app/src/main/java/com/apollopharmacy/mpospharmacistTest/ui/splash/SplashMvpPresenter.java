package com.apollopharmacy.mpospharmacistTest.ui.splash;

import com.apollopharmacy.mpospharmacistTest.di.PerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void decideNextActivity();

    void getGlobalConfigration();

//    void enableKioskMode();
}
