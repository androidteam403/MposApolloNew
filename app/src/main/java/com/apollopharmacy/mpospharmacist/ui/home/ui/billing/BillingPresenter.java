package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BillingPresenter<V extends BillingMvpView> extends BasePresenter<V>
        implements BillingMvpPresenter<V> {

    @Inject
    public BillingPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
