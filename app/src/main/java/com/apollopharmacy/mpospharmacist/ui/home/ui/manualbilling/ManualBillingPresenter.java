package com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterMvpView;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManualBillingPresenter<V extends ManualBillingMvpView> extends BasePresenter<V>
        implements ManualBillingMvpPresenter<V> {

    @Inject
    public ManualBillingPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
