package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface BatchListMvpView extends MvpView {

    void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onCheckBoxClick(GetBatchInfoRes.BatchListObj item, int position, double reservedqty);

    void onUncheckBoxClick(GetBatchInfoRes.BatchListObj batchListModel, double reqqty);

    void checkBatchInventorySuccess(CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(CheckBatchInventoryRes body);

    void onAddItemsClicked(boolean isBarcodeScannerItem);

    void onClickNotify();

    void noOrderFound(int count);

    void onNavigateNextActivity();

    void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj);

    void onClickSelectedBatch(GetBatchInfoRes.BatchListObj batchListModel, boolean isBarcodeScannedItem);

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();
    void onSuccessGetResonListApiCall(ReasonListResponse reasonListResponse);
    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse, Dialog dialog);

    void onClickAutoUpdate();

    void checkBatchInventorySuccess(String status, CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(String message);

    void onClickBack();

    void onClickNotAvailableBtn();

    void onClickScanBatchId();

    void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse);

    void onClickGetBatchList();
}
