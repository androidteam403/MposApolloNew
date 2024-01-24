package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.scanner;

public interface PickupSummaryCaptureManagerCallback {
    void onBackPressed();

    void onHeaderBarcodeScanned(boolean isMatched);

    void onScanned();

    void dialogShow(int orderPos);

    void onCompleteScan();

    void invalidBarcodeScanned();
}
