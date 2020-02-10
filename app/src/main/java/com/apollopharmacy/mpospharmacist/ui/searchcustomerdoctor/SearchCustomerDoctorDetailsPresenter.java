package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchCustomerDoctorDetailsPresenter<V extends SearchCustomerDoctorDetailsMvpView> extends BasePresenter<V>
        implements SearchCustomerDoctorDetailsMvpPresenter<V> {

    @Inject
    public SearchCustomerDoctorDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCustomerSearchClick() {
        getMvpView().onCustomerSearchClick();
    }

    @Override
    public void onDoctorSearchClick() {
        getMvpView().onDoctorSearchClick();
    }

    @Override
    public void onCorporateSearchClick() {
        getMvpView().onCorporateSearchClick();
    }

    @Override
    public void onActionBarBackPress() {
        getMvpView().onBackPressedClick();
    }
}
