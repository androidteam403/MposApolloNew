package com.apollo.pharmacy.ui.newadminloginsetup;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginMvpPresenter;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginMvpView;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NewAdminLoginPresenter <V extends NewAdminLoginMvpView> extends BasePresenter<V>
        implements NewAdminLoginMvpPresenter<V> {
    @Inject
    public NewAdminLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClick() {
        getMvpView().onLoginClick();
    }
}
