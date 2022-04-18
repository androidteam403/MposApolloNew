package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface BatchListMvpPresenter<V extends BatchListMvpView> extends MvpPresenter<V> {
    void getBatchDetailsApi(String itemId);

    void onAddItemsPressed();

    void checkBatchInventory(String reqqty, String batchNo, double itemID);
}
