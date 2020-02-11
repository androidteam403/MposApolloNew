package com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AllDoctorsDialogPresenter<V extends AllDoctorsDialogMvpView> extends BasePresenter<V>
        implements AllDoctorsDialogMvpPresenter<V> {

    @Inject
    public AllDoctorsDialogPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void dismissDialog() {
        getMvpView().dismissDialog();
    }
}
