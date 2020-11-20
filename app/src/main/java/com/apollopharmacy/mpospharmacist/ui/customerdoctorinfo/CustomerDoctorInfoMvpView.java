package com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface CustomerDoctorInfoMvpView extends MvpView {

    void onClickBackPressed();

    void onSucessPlayList();

    Context getContext();
}
