package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

public interface CallbackCaptureManager {

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void onFailureMessage(String message);
}
