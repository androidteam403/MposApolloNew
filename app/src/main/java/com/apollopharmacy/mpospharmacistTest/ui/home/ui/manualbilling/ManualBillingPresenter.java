package com.apollopharmacy.mpospharmacistTest.ui.home.ui.manualbilling;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManualBillingPresenter<V extends ManualBillingMvpView> extends BasePresenter<V>
        implements ManualBillingMvpPresenter<V> {

    @Inject
    public ManualBillingPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
