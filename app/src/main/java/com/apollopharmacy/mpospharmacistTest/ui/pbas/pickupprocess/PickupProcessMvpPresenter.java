package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;

import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

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

    void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId, boolean isRackAdapterClick);

    void onClickSkip();

    void getBatchDetailsApiCall(GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader);

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items, int qty, String finalStatus, boolean isRackAdapterClick);

    void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList);

    void onClickForwardToPacker();

    void mposPickPackOrderReservationApiCalls(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeaderList);

    void onClickOnHoldAll();

    void unPickUpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest, boolean isLastPos, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList);

    GetGlobalConfingRes getGlobalConfigRes();

    void getReasonList();

    void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String reasonCode, Dialog dialog);
}
