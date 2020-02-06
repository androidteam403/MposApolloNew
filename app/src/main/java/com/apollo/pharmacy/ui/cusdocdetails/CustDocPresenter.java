package com.apollo.pharmacy.ui.cusdocdetails;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.ui.customerdoctordetails.SearchCustomerDetailsMvpPresenter;
import com.apollo.pharmacy.ui.customerdoctordetails.SearchCustomerDetailsMvpView;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CustDocPresenter <V extends CustDocMvpView> extends BasePresenter<V>
        implements CustDocMvpPresenter<V> {

    @Inject
    public CustDocPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
