package com.apollopharmacy.mpospharmacist.ui.adminlogin;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AdminLoginPresenter<V extends AdminLoginMvpView> extends BasePresenter<V>
        implements AdminLoginMvpPresenter<V> {
    @Inject
    public AdminLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClick() {
        getMvpView().onLoginClick();
    }

    @Override
    public void onSetUpClick() {
        getMvpView().onSetUpClick();
    }

    @Override
    public void insertAdminLoginDetails() {
        getDataManager().setAdminSetUpFinish(true);
        getMvpView().onNavigateHomeScreen();
    }
}
