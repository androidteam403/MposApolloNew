package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SelectAppFlowPresenter<V extends SelectAppFlowMvpView> extends BasePresenter<V>
        implements SelectAppFlowMvpPresenter<V> {
    @Inject
    public SelectAppFlowPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickContinue() {
        getMvpView().onClickContinue();
    }

    @Override
    public void onClickLogout() {
        getDataManager().logoutUser();
        getMvpView().onClickLogout();
    }
}
