package com.apollopharmacy.mpospharmacistTest.ui.batchonfo.dialog;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DateChangePresenter <V extends DateChangeMvpView> extends BasePresenter<V>
        implements DateChangeMvpPresenter<V>{

    @Inject
    public DateChangePresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
