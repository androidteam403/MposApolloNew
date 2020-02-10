package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OrdersPresenter<V extends OrdersMvpView> extends BasePresenter<V>
        implements OrdersMvpPresenter<V> {

    @Inject
    public OrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onReturnClick() {
        getMvpView().onReturnClick();
    }

    @Override
    public void onCancelCLick() {
        getMvpView().onCancelCLick();
    }

    @Override
    public void onReOrderClick() {
        getMvpView().onReOrderClick();
    }

    @Override
    public void onItemClick() {
        getMvpView().onItemClick();
    }
}
