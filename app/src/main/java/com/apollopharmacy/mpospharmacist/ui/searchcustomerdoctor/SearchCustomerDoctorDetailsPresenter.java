package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
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

    @Override
    public void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity) {
        getMvpView().customerEditClick(customerEntity);
    }

    @Override
    public void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        getMvpView().onDoctorEditClick(doctorEntity, salesEntity);
    }

    @Override
    public void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity) {
        getMvpView().onCorporateEditClick(corporateEntity);
    }

    @Override
    public void onContinueBtnClick() {
        getMvpView().onContinueBtnClick();
    }
}
