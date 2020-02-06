package com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SalesPresenter<V extends SalesMvpView> extends BasePresenter<V> implements SalesMvpPresenter<V> {
    @Inject
    public SalesPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onSearchProductClick() {
        getMvpView().onProductSearchClick();
    }
}
