package com.apollo.pharmacy.ui.dashboard.fragments.sales;

import com.apollo.pharmacy.di.PerActivity;
import com.apollo.pharmacy.ui.base.MvpPresenter;

@PerActivity
public interface SalesMvpPresenter<V extends SalesMvpView> extends MvpPresenter<V> {
    void onSearchProductClick();
}
