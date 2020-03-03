package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpView;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter<V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityMvpPresenter<V> {
    @Inject
    public MainActivityPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName() +"\n"+ getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {
        return getDataManager().getGlobalJson().getStoreName() +"\n"+ getDataManager().getStoreId();
    }
}
