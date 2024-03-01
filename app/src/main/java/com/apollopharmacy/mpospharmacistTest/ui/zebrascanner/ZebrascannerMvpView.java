package com.apollopharmacy.mpospharmacistTest.ui.zebrascanner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface ZebrascannerMvpView extends MvpView {

    void scanqrcode(String s);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

   // void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeader, String userName, String storeId, String terminalId, String eposUrl, String barcode, String dataAreaId);
   void onClickScanCode(String s, String refno);

    void dialogShow(int orderPos);

    void scannedListener(List<String> barcodeList);
}
