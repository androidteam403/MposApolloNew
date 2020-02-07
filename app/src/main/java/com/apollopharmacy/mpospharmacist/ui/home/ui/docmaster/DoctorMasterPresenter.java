package com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardMvpView;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DoctorMasterPresenter<V extends DoctorMasterMvpView> extends BasePresenter<V>
        implements DoctorMasterMvpPresenter<V> {

    @Inject
    public DoctorMasterPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
