package com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales;

import com.apollopharmacy.mpospharmacist.di.PerActivity;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

@PerActivity
public interface SalesMvpPresenter<V extends SalesMvpView> extends MvpPresenter<V> {
    void onSearchProductClick();
}
