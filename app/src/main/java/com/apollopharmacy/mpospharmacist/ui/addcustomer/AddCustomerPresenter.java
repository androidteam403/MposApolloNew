package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddCustomerPresenter<V extends AddCustomerMvpView> extends BasePresenter<V>
        implements AddCustomerMvpPresenter<V> {
    @Inject
     AddCustomerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDateClick() {
        getMvpView().onDateClick();
    }

    @Override
    public void onClickSubmit() {
        getMvpView().onSubmitClick();
    }

    @Override
    public void onClickAnniversary() {
        getMvpView().onAnniversaryClick();
    }

    @Override
    public void onClickRegistration() {
        getMvpView().onRegistrationClick();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void userSubmit() {
        if (getMvpView().isNetworkConnected()) {
            AddCustomerModel addCustomerModel = new AddCustomerModel();
            addCustomerModel.setFirstName(getMvpView().getfirstName());
            addCustomerModel.setMobile(getMvpView().getmobile());
            addCustomerModel.setCardNumber(getMvpView().getcardNumber());
        }
    }
}
