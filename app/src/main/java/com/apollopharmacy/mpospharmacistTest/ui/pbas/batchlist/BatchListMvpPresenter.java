package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface BatchListMvpPresenter<V extends BatchListMvpView> extends MvpPresenter<V> {
    void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId);

    void onAddItemsClicked();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos);

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();

    void onClickAutoUpdate();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items, Integer qty, String finalStatus);

    void onClickBack();

    void onClickNotAvailableBtn();

    GetGlobalConfingRes getGlobalConfigRes();
}
