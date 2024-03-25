package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AdminPresenter<V extends AdminMvpView> extends BasePresenter<V> implements AdminMvpPresenter<V> {
    @Inject
    public AdminPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
