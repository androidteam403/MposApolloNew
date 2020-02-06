package com.apollopharmacy.mpospharmacist.ui.searchuser;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchUserPresenter<V extends SearchUserMvpView> extends BasePresenter<V>
        implements SearchUserMvpPresenter<V> {
    @Inject
    public SearchUserPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickAdd() {
        getMvpView().onClickAdd();
    }
}
