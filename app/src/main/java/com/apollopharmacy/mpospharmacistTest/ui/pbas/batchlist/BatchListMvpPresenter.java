package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

public interface BatchListMvpPresenter<V extends BatchListMvpView> extends MvpPresenter<V> {
    void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId);

    void onAddItemsClicked();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos);
}
