package com.apollo.pharmacy.ui.dashboard.fragments.sales;

import androidx.databinding.ObservableField;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

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
