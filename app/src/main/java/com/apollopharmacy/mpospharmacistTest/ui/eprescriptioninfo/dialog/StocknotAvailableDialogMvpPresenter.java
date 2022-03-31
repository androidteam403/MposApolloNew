package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface StocknotAvailableDialogMvpPresenter <V extends StocknotAvailableDialogMvpView> extends MvpPresenter<V>
{
    void dismissDialog();
}
