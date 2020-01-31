package com.apollo.pharmacy.ui.adduser;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddUserPresenter<V extends AddUserMvpView> extends BasePresenter<V>
        implements AddUserMvpPresenter<V> {
    @Inject
    public AddUserPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDateClick() {
        getMvpView().onDateClick();
    }
}
