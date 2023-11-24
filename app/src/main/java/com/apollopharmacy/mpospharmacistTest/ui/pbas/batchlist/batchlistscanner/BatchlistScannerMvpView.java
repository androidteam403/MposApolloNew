package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

public interface BatchlistScannerMvpView extends MvpView {
    void onClickClose();

    void onClickSkip();

    void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse);

    void onClickReferToAdmin();

    void onClickOnHold();

    void onSuccessGetResonListApiCall(ReasonListResponse reasonListResponse);

    void noOrderFound(int count);

    void onClickSelectedBatch(GetBatchInfoRes.BatchListObj batchListModel, boolean isBarcodeScannedItem);

    void checkBatchInventorySuccess(CheckBatchInventoryRes body);
    void onAddItemsClicked(boolean isBarcodeScannerItem);

    void onClickSearchTextClear();

    void onClickProductInfo();

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse, Dialog dialog);
}
