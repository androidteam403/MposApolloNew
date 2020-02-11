package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

public interface DoctorDetailsMvpView extends MvpView {
    void onAddDoctorClick();

    void onClickBackPressed();

    void getDoctorSearchList(DoctorSearchResModel model);
}
