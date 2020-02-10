package com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface CustomerDoctorInfoMvpPresenter<V extends CustomerDoctorInfoMvpView> extends MvpPresenter<V> {

    void onClickBackPress();
}
