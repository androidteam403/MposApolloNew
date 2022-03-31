package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface EPrescriptionDialogMvpPresenter<V extends EPrescriptionDialogMvpView> extends MvpPresenter<V> {

    void dismissDialog();
}
