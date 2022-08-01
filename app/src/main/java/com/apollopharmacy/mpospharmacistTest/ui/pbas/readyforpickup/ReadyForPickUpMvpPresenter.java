package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface ReadyForPickUpMvpPresenter<V extends ReadyForPickUpMvpView> extends MvpPresenter<V> {
    void onClickStartPickup();

    void onClickBack();

    void cancel();

    void onClickTakePrint(TransactionHeaderResponse.OMSHeader omsHeader);

    void onClickStartPickingWithoutQrCode();

    void onClickScanCode();

    void onClickPrint();

    void mposPickPackOrderReservationApiCall(int requestStatus, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList);

    String userName();

    String storeId();

    String terminalId();

    String eposUrl();
}
