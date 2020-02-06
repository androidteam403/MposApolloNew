package com.apollopharmacy.mpospharmacist.ui.adduser;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

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
}
