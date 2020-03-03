package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpView;

public interface MainActivityMvpPresenter<V extends MainActivityMvpView> extends MvpPresenter<V> {
    String getLoginUserName();

    String getLoinStoreLocation();
}
