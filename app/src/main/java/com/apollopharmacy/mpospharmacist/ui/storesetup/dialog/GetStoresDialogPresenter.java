package com.apollopharmacy.mpospharmacist.ui.storesetup.dialog;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class GetStoresDialogPresenter<V extends GetStoresDialogMvpView> extends BasePresenter<V>
        implements GetStoresDialogMvpPresenter<V> {

    @Inject
    public GetStoresDialogPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void dismissDialog() {
        getMvpView().dismissDialog();
    }
}
