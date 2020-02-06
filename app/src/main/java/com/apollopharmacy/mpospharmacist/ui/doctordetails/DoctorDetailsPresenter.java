package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DoctorDetailsPresenter<V extends DoctorDetailsMvpView> extends BasePresenter<V>
        implements DoctorDetailsMvpPresenter<V> {

    @Inject
    public DoctorDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onNewDoctor() {
        getMvpView().onDoctorNew();
    }
}
