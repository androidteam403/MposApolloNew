package com.apollo.pharmacy.ui.customerdoctordetails;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.ui.newadminloginsetup.NewAdminLoginMvpPresenter;
import com.apollo.pharmacy.ui.newadminloginsetup.NewAdminLoginMvpView;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchCustomerDetailsPresenter <V extends SearchCustomerDetailsMvpView> extends BasePresenter<V>
        implements SearchCustomerDetailsMvpPresenter<V> {

    @Inject
    public SearchCustomerDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onPhoneClick() {
        getMvpView().onPhoneClick();
    }

    @Override
    public void onRegisterClick() {
        getMvpView().onRegisterClick();
    }
}
