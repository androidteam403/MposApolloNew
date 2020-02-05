package com.apollo.pharmacy.ui.adddoctor;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface AddDoctorMvpPresenter<V extends AddDoctorMvpView> extends MvpPresenter<V> {
    void onClickSubmit();

}
