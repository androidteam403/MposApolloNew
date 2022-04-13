package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ReadyForPickUpPresenter<V extends ReadyForPickUpMvpView> extends BasePresenter<V>
        implements ReadyForPickUpMvpPresenter<V> {

    @Inject
    public ReadyForPickUpPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickStartPickup() {

        getMvpView().onClickStartPickup();
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void cancel() {
getMvpView().cancel();
    }

    @Override
    public void onClickTakePrint() {
        getMvpView().onClickTakePrint();
    }

    @Override
    public void onClickStartPickingWithoutQrCode() {
        getMvpView().onClickStartPickingWithoutQrCode();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }
}
