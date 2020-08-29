package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;

public interface DoctorDetailsMvpPresenter<V extends DoctorDetailsMvpView> extends MvpPresenter<V> {
    void onAddDoctorClick();

    void onBackPressedClick();

    void onAllDoctorsClick();

    void getDoctorsList();

    void getSalesOrigin();

    void getAllDoctorsList();

    void onSubmitClick();

    void onCustomDoctorLayoutClick();

    String getStoreName();

    String getStoreId();

    String getTerminalId();
}
