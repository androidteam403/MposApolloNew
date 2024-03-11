package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.zebraselfidscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface ZebraSelfscannerMvpPresenter <V extends ZebraSelfscannerMvpView> extends MvpPresenter<V>
{
    void onClickClose();

    GetGlobalConfingRes getGlobalConfiguration();
}
