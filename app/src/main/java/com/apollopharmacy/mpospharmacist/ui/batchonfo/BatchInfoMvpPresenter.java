package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

public interface BatchInfoMvpPresenter<V extends BatchInfoMvpView> extends MvpPresenter<V> {
    void onClickIncrement();

    void onClickDecrement();

    void onNavigateNextActivity();

    void onActionBarBackPressed();

    void getBatchDetailsApi(GetItemDetailsRes.Items selected_item);
}
