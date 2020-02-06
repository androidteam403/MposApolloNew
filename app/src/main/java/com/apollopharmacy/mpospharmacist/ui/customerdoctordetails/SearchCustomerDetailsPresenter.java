package com.apollopharmacy.mpospharmacist.ui.customerdoctordetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

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
