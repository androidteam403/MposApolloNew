package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

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
