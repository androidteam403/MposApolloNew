package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface DoctorDetailsMvpPresenter<V extends DoctorDetailsMvpView> extends MvpPresenter<V> {
    void onAddDoctorClick();

    void onBackPressedClick();

    void onAllDoctorsClick();

    void getDoctorsList();

    void getSalesOrigin();

    void getAllDoctorsList();
}
