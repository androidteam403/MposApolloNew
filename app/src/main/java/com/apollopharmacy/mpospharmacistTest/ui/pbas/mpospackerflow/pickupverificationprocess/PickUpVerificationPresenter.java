package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PickUpVerificationPresenter<V extends PickUpVerificationMvpView> extends BasePresenter<V>
        implements PickUpVerificationMvpPresenter<V> {

    @Inject
    public PickUpVerificationPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onPartialWarningYesClick() {
        getMvpView().onPartialWarningYesClick();
    }

    @Override
    public void onPartialWarningNoClick() {
        getMvpView().onPartialWarningNoClick();
    }

    @Override
    public void onClickReVerificatio() {
        getMvpView().onClickReVerificatio();
    }

    @Override
    public void onClickVerification() {
        getMvpView().onClickVerification();
    }

}
