package com.apollo.pharmacy.ui.customerdetails;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.adduser.AddUserMvpPresenter;
import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerDetailsPresenter<V extends CustomerDetailsMvpView> extends BasePresenter<V>
        implements CustomerDetailsMvpPresenter<V> {
    @Inject
    public CustomerDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onNewCustomer() {
        getMvpView().onCustomerNew();
    }
}
