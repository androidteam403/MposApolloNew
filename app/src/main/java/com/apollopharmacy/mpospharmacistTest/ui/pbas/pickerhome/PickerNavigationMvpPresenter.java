package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface PickerNavigationMvpPresenter<V extends PickerNavigationMvpView> extends MvpPresenter<V> {
    String getLoginUserName();

    String getLoinStoreLocation();
}
