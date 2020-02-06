package com.apollopharmacy.mpospharmacist.ui.adduser;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AddUserMvpPresenter<V extends AddUserMvpView> extends MvpPresenter<V> {

    void onDateClick();

    void onClickSubmit();

    void onClickAnniversary();

    void onClickRegistration();
}
