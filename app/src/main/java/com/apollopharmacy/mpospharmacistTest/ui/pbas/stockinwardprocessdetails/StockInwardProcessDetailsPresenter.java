package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StockInwardProcessDetailsPresenter<V extends StockInwardProcessDetailsMvpView> extends BasePresenter<V>
        implements StockInwardProcessDetailsMvpPresenter<V> {

    @Inject
    public StockInwardProcessDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }
}
