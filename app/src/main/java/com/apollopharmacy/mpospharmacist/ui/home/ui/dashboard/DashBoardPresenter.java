package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpView;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DashBoardPresenter<V extends DashBoardMvpView> extends BasePresenter<V>
        implements DashBoardMvpPresenter<V> {

    @Inject
    public DashBoardPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
