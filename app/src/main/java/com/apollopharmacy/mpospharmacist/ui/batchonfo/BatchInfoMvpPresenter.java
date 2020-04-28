package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

public interface BatchInfoMvpPresenter<V extends BatchInfoMvpView> extends MvpPresenter<V> {
    void onClickIncrement();

    void onClickDecrement();

    void onNavigateNextActivity();

    void onActionBarBackPressed();

    void getBatchDetailsApi(SalesLineEntity selected_item);

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items);
}
