package com.apollopharmacy.mpospharmacist.ui.splash;

import android.os.Handler;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {
    private static final long SPLASH_DISPLAY_LENGTH = 2000;

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(this::decideNextActivity, SPLASH_DISPLAY_LENGTH);
    }

    private void decideNextActivity() {
        if (getDataManager().isAdminSetUpFinish()) {
            getMvpView().openLoginActivity();
        } else {
            getMvpView().openAdminSetupActivity();
        }
    }
}
