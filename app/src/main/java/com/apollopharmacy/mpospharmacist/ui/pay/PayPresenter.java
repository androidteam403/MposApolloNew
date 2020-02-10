package com.apollopharmacy.mpospharmacist.ui.pay;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PayPresenter<V extends PayMvpView> extends BasePresenter<V>
        implements PayMvpPresenter<V> {

    @Inject
    public PayPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void NavigateToHomeScreen() {
        getMvpView().NavigateToHomeScreen();
    }
}
