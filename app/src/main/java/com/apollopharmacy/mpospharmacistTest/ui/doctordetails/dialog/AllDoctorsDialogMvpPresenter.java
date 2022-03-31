package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface AllDoctorsDialogMvpPresenter<V extends AllDoctorsDialogMvpView> extends MvpPresenter<V> {

    void dismissDialog();

    void onAddDoctorClick();

    void searchDoctorDetailsByName();

    void defaultDoctorList();

}
