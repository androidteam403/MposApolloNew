package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

public interface ProductListMvpView extends MvpView {

    void onClickBackBtn();

    void onClickProductItem(ProductList item);
}
