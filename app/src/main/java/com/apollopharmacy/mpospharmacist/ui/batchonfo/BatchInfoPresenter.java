package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BatchInfoPresenter<V extends BatchInfoMvpView> extends BasePresenter<V>
        implements BatchInfoMvpPresenter<V> {
    @Inject
    public BatchInfoPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickIncrement() {
        getMvpView().onIncrementClick();
    }

    @Override
    public void onClickDecrement() {
        getMvpView().onDecrementClick();
    }

    @Override
    public void onNavigateNextActivity() {
        getMvpView().onNavigateNextActivity();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }
}
