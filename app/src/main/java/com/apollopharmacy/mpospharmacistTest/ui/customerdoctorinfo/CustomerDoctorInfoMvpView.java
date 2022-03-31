package com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface CustomerDoctorInfoMvpView extends MvpView {

    void onClickBackPressed();

    void onSucessPlayList();

    Context getContext();
}
