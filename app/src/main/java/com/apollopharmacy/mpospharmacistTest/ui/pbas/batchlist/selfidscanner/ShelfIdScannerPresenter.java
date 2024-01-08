package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.selfidscanner;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ShelfIdScannerPresenter<V extends ShelfIdScannerMvpView> extends BasePresenter<V>
        implements ShelfIdScannerMvpPresenter<V> {
    @Inject
    public ShelfIdScannerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickClose() {
        getMvpView().onClickClose();
    }
}
