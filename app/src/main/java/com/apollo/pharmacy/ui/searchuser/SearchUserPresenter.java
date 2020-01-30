package com.apollo.pharmacy.ui.searchuser;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

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
