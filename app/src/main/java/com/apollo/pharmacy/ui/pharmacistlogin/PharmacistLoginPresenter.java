package com.apollo.pharmacy.ui.pharmacistlogin;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PharmacistLoginPresenter<V extends PharmacistLoginMvpView> extends BasePresenter<V>
        implements PharmacistLoginMvpPresenter<V> {
    @Inject
    public PharmacistLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickLogin() {
        getMvpView().onIntentCall();
    }

    @Override
    public void onClickSendOtp() {
        if (getMvpView().getMobile().length() < 10) {
            getMvpView().showInputMobileError("Enter mobile number");
        } else {
            getMvpView().onSuccessSendOtp();
        }
    }

    @Override
    public void onClickVerifyOtp() {
        if (getMvpView().getOtp().length() < 6) {
            getMvpView().showMessage("Enter OTP");
        } else {
            getDataManager().setUserMobile(getMvpView().getMobile());
            getDataManager().setUserLogin(true);
            getMvpView().onSuccessLogin();
        }
    }
}
