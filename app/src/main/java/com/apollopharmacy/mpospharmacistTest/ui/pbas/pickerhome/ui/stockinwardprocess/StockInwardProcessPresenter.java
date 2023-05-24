package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StockInwardProcessPresenter<V extends StockInwardProcessMvpView> extends BasePresenter<V>
        implements StockInwardProcessMvpPresenter<V> {

    @Inject
    public StockInwardProcessPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickFromDate() {
        getMvpView().onClickFromDate();
    }

    @Override
    public void onClickToDate() {
        getMvpView().onClickToDate();
    }
}
