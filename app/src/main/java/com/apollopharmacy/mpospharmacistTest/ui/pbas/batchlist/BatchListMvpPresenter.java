package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface BatchListMvpPresenter<V extends BatchListMvpView> extends MvpPresenter<V> {
    void getBatchDetailsApi(String itemId);

    void onAddItemsClicked();

    void checkBatchInventory(double reqqty, String batchNo, String itemID);
}
