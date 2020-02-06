package com.apollopharmacy.mpospharmacist.ui.cusdocdetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CustDocPresenter<V extends CustDocMvpView> extends BasePresenter<V>
        implements CustDocMvpPresenter<V> {

    @Inject
    public CustDocPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
