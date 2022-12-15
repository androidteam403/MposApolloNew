package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

public interface ReadyForPickUpMvpView extends MvpView {
    void onTagBoxClick(String fullfillmentId, int position);

    void onDeleteClick(int position, String fullfillmentId, String s);

    void onClickStartPickup();

    void onClickBack();

    void cancel();
//


    void onClickTakePrint(TransactionHeaderResponse.OMSHeader omsHeader);

    void onClickStartPickingWithoutQrCode();

    void onClickScanCode();

    void onClickPrint();

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);
}
