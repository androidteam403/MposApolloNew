package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AddDoctorMvpPresenter<V extends AddDoctorMvpView> extends MvpPresenter<V> {

    void onClickSubmit();

    void onActionBarBackPressed();

    void userSubmit( );

}
