package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner;

import java.util.List;

public interface CaptureManagerCallback {
    void scannedListener(List<String> barcodeList);
}
