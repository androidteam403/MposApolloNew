package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PickerNavigationPresenter<V extends PickerNavigationMvpView> extends BasePresenter<V>
        implements PickerNavigationMvpPresenter<V> {
    @Inject
    public PickerNavigationPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName() + "\n" + getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {
        return getDataManager().getGlobalJson().getStoreName() + "\n" + getDataManager().getStoreId();
    }
}

