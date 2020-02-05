package com.apollo.pharmacy.ui.storesetup;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.ui.dashboard.DashboardMvpPresenter;
import com.apollo.pharmacy.ui.dashboard.DashboardMvpView;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

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
