package com.apollopharmacy.mpospharmacist.ui.storesetup;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StoreSetupPresenter <V extends StoreSetupMvpView> extends BasePresenter<V>
        implements StoreSetupMvpPresenter<V> {
    @Inject
    public StoreSetupPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onClickSelectStore() {
        getMvpView().onSelectStoreClick();
    }
}
