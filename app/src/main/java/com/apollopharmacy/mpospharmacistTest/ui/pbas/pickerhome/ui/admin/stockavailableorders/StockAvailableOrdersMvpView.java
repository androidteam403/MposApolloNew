package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface StockAvailableOrdersMvpView extends MvpView {
    void onClickBack();

    void onClickUnAssign(GetOMSTransactionHeaderResponse.OMSHeader omsHeader, String userId);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void onClickAssign(GetOMSTransactionHeaderResponse.OMSHeader omsHeader);

    void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList);
}
