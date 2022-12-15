package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

public interface OnHoldMvpView extends MvpView {

    void onSuccessGetOMSHeaderTransactionApi(TransactionHeaderResponse omsHeader);

    void noOrderFound(int count);

    void onClickScanCode();

    void onClickSearchClear();

    void onClickUnHold(TransactionHeaderResponse.OMSHeader omsHeader);

    void onSuccessMposPickPackOrderReservationApiCall(MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void onClickFilterIcon();


    void setStoresList(TransactionHeaderResponse body);
}
