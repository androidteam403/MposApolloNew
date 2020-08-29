package com.apollopharmacy.mpospharmacist.ui.batchonfo.dialog;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DateChangePresenter <V extends DateChangeMvpView> extends BasePresenter<V>
        implements DateChangeMvpPresenter<V>{

    @Inject
    public DateChangePresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
