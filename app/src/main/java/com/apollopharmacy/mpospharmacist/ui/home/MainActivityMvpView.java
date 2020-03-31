package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface MainActivityMvpView extends MvpView {

    void navigateLoginActivity();

    void displayAppInfoDialog(String title,String subTitle,String positiveBtn,String negativeBtn);
}
