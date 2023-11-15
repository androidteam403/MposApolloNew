package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface BatchlistScannerMvpPresenter<V extends BatchlistScannerMvpView> extends MvpPresenter<V> {
    void onClickClose();

    void onClickSkip();

    void getBatchDetailsByBarCode(String s, String itemId);

    void onClickReferToAdmin();

    void onClickOnHold();

    void getReasonList();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos);

    void onClickSearchTextClear();

    void onClickProductInfo();

    void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String reasonCode, Dialog dialog);
}
