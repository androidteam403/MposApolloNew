package com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerDoctorInfoPresenter<V extends CustomerDoctorInfoMvpView> extends BasePresenter<V>
        implements CustomerDoctorInfoMvpPresenter<V> {

    @Inject
    public CustomerDoctorInfoPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
