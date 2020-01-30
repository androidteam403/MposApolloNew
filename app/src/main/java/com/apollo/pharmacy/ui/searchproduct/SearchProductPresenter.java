package com.apollo.pharmacy.ui.searchproduct;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchProductPresenter<V extends SearchProductMvpView> extends BasePresenter<V>
        implements SearchProductMvpPresenter<V> {
    @Inject
    public SearchProductPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
