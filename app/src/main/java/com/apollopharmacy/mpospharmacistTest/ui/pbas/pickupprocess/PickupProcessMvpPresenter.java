package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;

import java.util.List;

public interface PickupProcessMvpPresenter<V extends PickupProcessMvpView> extends MvpPresenter<V> {


    void onClickBack();

    void onClickContinue();

    void onRackApiCall();

    void onClickStausIcon();

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest, String requestType);

    void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId);

    void onClickSkip();

    void getBatchDetailsApiCall(GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader);

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items, int qty, String finalStatus);

    void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList);

    void onClickForwardToPacker();

    void mposPickPackOrderReservationApiCalls(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeaderList);

    void onClickOnHoldAll();

}
