package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface PickUpSummaryMvpView extends MvpView {

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList();

    List<List<RackAdapter.RackBoxModel.ProductData>> productList();

    String fullCount(String fullCount);
    void onClickPrint(TransactionHeaderResponse.OMSHeader omsHeader);


    void OmsOrderUpdateSuccess(OMSOrderForwardResponse response);

    void OmsOrderUpdateFailure(OMSOrderForwardResponse response);

    String partialCount(String partialCount);

    void Forward_To_Pickerconfirmation();

    String notAvailable(String notAvailableCount);

    void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response);

    void OmsOrderUpdateFailure(OMSOrderUpdateResponse response);

    void onClickItem(int pos);

    void onClickScanCode();

    void onClickUpdateOMSOrder_pickingconfirmation();

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);


}
