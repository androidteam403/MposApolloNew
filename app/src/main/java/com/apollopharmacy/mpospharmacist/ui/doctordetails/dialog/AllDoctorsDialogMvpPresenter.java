package com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AllDoctorsDialogMvpPresenter<V extends AllDoctorsDialogMvpView> extends MvpPresenter<V> {

    void dismissDialog();

    void onAddDoctorClick();

    void searchDoctorDetailsByName();

    void defaultDoctorList();

}
