package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MedicineDetailsPresenter<V extends MedicineDetailsMvpView> extends BasePresenter<V>
        implements MedicineDetailsMvpPresenter<V> {

    @Inject
    public MedicineDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onManualSearchClick() {
        getMvpView().onManualSearchClick();
    }

    @Override
    public void onVoiceSearchClick() {
        getMvpView().onVoiceSearchClick();
    }

    @Override
    public void onBarCodeSearchClick() {
        getMvpView().onBarCodeSearchClick();
    }

    @Override
    public void onBackPressClick() {
        getMvpView().onClickBackBtn();
    }

    @Override
    public void onPayButtonClick() {
        getMvpView().onPayButtonClick();
    }
}