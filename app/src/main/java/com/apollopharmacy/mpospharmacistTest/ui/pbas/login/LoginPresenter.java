package com.apollopharmacy.mpospharmacistTest.ui.pbas.login;

import android.os.Handler;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacistTest.data.utils.LoggedInMode;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {
    @Inject
    public LoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClick() {
        if (getMvpView() != null) {
            if (getMvpView().getEmail().trim().equals("") || getMvpView().getPassword().trim().equals("")) {
                getMvpView().showInputError();
            } else {
                getMvpView().showLoading();
                new Handler().postDelayed(() -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    // set demo user
                    UserProfile mProfile = new UserProfile();
                    mProfile.setFirstName("Dinesh");
                    mProfile.setLastName("Kumar");
                    mProfile.setEmail("dinesh@gmail.com");
                    mProfile.setUserId("1");
                    //update preferences
                    getDataManager().updateUserInfo("access toekn", 1l, LoggedInMode.LOGGED_IN_MODE_SERVER, "", mProfile.getEmail(), "");
                    getMvpView().onLoginSuccess(mProfile);

                    getMvpView().hideLoading();
                }, 1000);

            }

        }


    }
}
