package com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface DoctorMasterMvpView extends MvpView {

    void onSubmitBtnClick();

    void onClickBackPressed();

    void addDoctorSuccess(AddDoctorResModel addDoctorResModel);

    void addDoctorFailed(String errMsg);

    String getDoctorName();

    String getDoctorRegNo();

    String getSpeciality();

    String getPlaceOfPractice();

    String getAddress();

    String getPhoneNo();

    void onSucessPlayList();

    Context getContext();
}
