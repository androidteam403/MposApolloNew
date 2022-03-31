package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EPrescriptionDialogPresenter<V extends EPrescriptionDialogMvpView> extends BasePresenter<V>
        implements EPrescriptionDialogMvpPresenter<V> {

    @Inject
    public EPrescriptionDialogPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void dismissDialog() {
        getMvpView().dismissDialog();
    }
}