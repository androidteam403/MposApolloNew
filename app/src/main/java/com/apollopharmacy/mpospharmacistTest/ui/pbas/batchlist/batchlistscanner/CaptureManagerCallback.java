package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public interface CaptureManagerCallback {
    void dialogShow(String scannedQty);

    void onCompleteScan(String trayId, List<GetBatchInfoRes.BatchListObj> salesLineBatchList);

    void enableCompleteBoxBtn();
}
