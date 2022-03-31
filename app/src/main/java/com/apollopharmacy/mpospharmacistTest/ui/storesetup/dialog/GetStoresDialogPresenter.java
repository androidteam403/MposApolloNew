package com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

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
