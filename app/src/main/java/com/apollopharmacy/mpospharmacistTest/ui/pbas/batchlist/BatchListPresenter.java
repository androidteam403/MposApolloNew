package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BatchListPresenter<V extends BatchListMvpView> extends BasePresenter<V>
        implements BatchListMvpPresenter<V> {
    @Inject
    public BatchListPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
