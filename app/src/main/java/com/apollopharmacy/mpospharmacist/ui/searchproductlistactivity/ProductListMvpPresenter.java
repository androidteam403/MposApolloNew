package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpView;

public interface ProductListMvpPresenter <V extends ProductListMvpView> extends MvpPresenter<V> {

    void onClickBackPress();

    void getProductDetails();

}
