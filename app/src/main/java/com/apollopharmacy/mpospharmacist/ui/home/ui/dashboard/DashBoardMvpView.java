package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ListDataEntity;

public interface DashBoardMvpView extends MvpView {

    void onClickNewOrderBtn();

    void onSucessPlayList();

    Context getContext();
}
