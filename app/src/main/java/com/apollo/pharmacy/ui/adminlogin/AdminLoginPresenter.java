package com.apollo.pharmacy.ui.adminlogin;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

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
