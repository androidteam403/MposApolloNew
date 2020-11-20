package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;

import java.util.ArrayList;

public interface DoctorDetailsMvpView extends MvpView {
    void onAddDoctorClick();

    void onClickBackPressed();

    void getDoctorSearchList(DoctorSearchResModel model);

    void getSalesOriginList(SalesOriginResModel model);

    void getAllDoctorsSearchList(DoctorSearchResModel model);

    void onAllDoctorsClick();

    void onSubmitClick();

    void onSelectDoctor(DoctorSearchResModel.DropdownValueBean dropdownValueBean, ArrayList<DoctorSearchResModel.DropdownValueBean> doctorList);

    void onCustomDoctorLayoutClick();

    void onSucessPlayList();

    Context getContext();
}
