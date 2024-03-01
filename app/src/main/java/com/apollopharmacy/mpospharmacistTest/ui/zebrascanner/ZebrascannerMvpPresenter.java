package com.apollopharmacy.mpospharmacistTest.ui.zebrascanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface ZebrascannerMvpPresenter <V extends ZebrascannerMvpView > extends MvpPresenter<V>
{
    void scanqrcode(String s);

    void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeader, String userName, String storeId, String terminalId, String eposUrl, String barcode, String dataAreaId);

    void onClickScanCode(String s, String refno);

    void dialogShow(int orderPos);

    void scannedListener(List<String> barcodeList);
}
