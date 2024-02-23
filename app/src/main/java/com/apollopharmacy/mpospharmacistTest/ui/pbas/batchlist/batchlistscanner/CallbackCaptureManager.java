package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

public interface CallbackCaptureManager {
    void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse);

    void onFailure(String message);
}
