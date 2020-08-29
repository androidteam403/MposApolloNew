package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface ProductListMvpPresenter<V extends ProductListMvpView> extends MvpPresenter<V> {

    void onClickBackPress();

    void getProductDetails();

    void onVoiceSearchClick();

    void onBarCodeClick();

    String getStoreName();

    String getStoreId();

    String getTerminalId();
}
