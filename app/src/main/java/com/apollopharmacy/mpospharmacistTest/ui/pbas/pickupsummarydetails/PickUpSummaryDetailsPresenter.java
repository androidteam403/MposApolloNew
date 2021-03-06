package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PickUpSummaryDetailsPresenter<V extends PickUpSummaryDetailsMvpView> extends BasePresenter<V>
        implements PickUpSummaryDetailsMvpPresenter<V> {
    @Inject
    public PickUpSummaryDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }
}
