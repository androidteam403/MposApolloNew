package com.apollo.pharmacy.ui.doctordetails;

import com.apollo.pharmacy.ui.base.MvpPresenter;
import com.apollo.pharmacy.ui.customerdetails.CustomerDetailsMvpView;

public interface DoctorDetailsMvpPresenter <V extends DoctorDetailsMvpView> extends MvpPresenter<V> {
    void onNewDoctor();
}
