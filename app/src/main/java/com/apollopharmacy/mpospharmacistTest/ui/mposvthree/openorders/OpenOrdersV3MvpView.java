package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface OpenOrdersV3MvpView extends MvpView {
    void setFiltersHeaderLists(List<TransactionHeaderResponse.OMSHeader> omsHeaderList, boolean isRefresh);

    void noOrderFound(int count);

    void onClickPrescriptionPreview(GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList, int position);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void ondownArrowClicked(String refId, int position);

    void onSuccessGetOmsTransactionAutoAssign(List<GetOMSTransactionResponse> getOMSTransactionResponseList);

    void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList);

    void onSuccessGetOmsTransactionBulkSelection(List<GetOMSTransactionResponse> body);

    void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body);

    void onFullfillmentItemClick(int pos, int itemPos, TransactionHeaderResponse.OMSHeader omsHeader);
}
