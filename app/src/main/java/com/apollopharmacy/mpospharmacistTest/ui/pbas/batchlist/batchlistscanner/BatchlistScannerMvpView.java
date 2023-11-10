package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;

import java.util.List;

public interface BatchlistScannerMvpView extends MvpView {
    void onClickClose();

    void onClickSkip();

    void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse);

    void onClickReferToAdmin();

    void onClickOnHold();

    void onSuccessGetResonListApiCall(ReasonListResponse reasonListResponse);
}
