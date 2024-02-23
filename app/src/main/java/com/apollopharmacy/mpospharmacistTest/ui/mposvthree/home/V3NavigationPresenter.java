package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.home;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class V3NavigationPresenter<V extends V3NavigationMvpView> extends BasePresenter<V> implements V3NavigationMvpPresenter<V> {
    @Inject
    public V3NavigationPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
