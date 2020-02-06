package com.apollopharmacy.mpospharmacist.ui.searchproduct;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchProductPresenter<V extends SearchProductMvpView> extends BasePresenter<V>
        implements SearchProductMvpPresenter<V> {
    @Inject
    public SearchProductPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
