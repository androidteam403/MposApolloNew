package com.apollo.pharmacy.ui.pharmacistlogin;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PharmacistLoginPresenter<V extends PharmacistLoginMvpView> extends BasePresenter<V>
        implements PharmacistLoginMvpPresenter<V> {
    @Inject
    public PharmacistLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickLogin() {
        getMvpView().onIntentCall();
    }
}
