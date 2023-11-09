package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

public interface BatchlistScannerMvpView extends MvpView {
    void onClickClose();

    void onClickSkip();

    void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse);

    void onClickReferToAdmin();

    void onClickOnHold();
}
