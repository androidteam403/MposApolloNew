package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OrderDetailsPresenter<V extends OrderDetailsMvpView> extends BasePresenter<V>
        implements OrderDetailsMvpPresenter<V> {
    @Inject
    public OrderDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSelectContinue() {
        getMvpView().onSelectContinue();
    }

    @Override
    public void onClickBackIcon() {
        getMvpView().onClickBackIcon();
    }

}
