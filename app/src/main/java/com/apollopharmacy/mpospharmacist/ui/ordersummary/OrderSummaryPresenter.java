package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OrderSummaryPresenter<V extends OrderSummaryMvpView> extends BasePresenter<V>
        implements OrderSummaryMvpPresenter<V> {
    private final int REQUEST_CODE_INITIALIZE = 10001;

    @Inject
    public OrderSummaryPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onBackOrderPressed() {
        getMvpView().onBackOrderPressed();
    }

    @Override
    public void onNewPlaceOrderClicked() {
        getMvpView().onNewPlaceOrderClicked();
    }
}
