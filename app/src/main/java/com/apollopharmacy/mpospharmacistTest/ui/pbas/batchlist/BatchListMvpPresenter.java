package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

import java.util.List;

public interface BatchListMvpPresenter<V extends BatchListMvpView> extends MvpPresenter<V> {
    void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId);

    void onAddItemsClicked();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos);

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();
    void getReasonList();
    void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String reasonCode, Dialog dialog);

    void onClickSkip();

    void onClickAutoUpdate();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items, Integer qty, String finalStatus);

    void onClickBack();

    void getBatchDetailsByBarCode(String s, String itemId);

    void onClickNotAvailableBtn();

    GetGlobalConfingRes getGlobalConfigRes();

    void onClickScanBatchId();

    void onClickGetBatchList();
}
