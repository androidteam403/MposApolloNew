package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;

import android.widget.Spinner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface PickupProcessMvpView extends MvpView {

    void onClickBack();

    void onClickContinue();


    void onClickFullPicked();

    void onClickStausIcon();

    void onClickBatchDetails(int orderAdapterPos, GetOMSTransactionResponse.SalesLine salesLine, int adapterPosition);

    void onClickStart(int position);

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();

    void OmsOrderUpdateSuccess(OMSOrderForwardResponse response, String requestType);

    void OmsOrderUpdateFailure(OMSOrderForwardResponse response);

    void onClickDropDown(Spinner spinner);

    void onSuccessRackApi(RacksDataResponse racksDataResponse);

    void onRackStatusUpdate(List<List<RackAdapter.RackBoxModel.ProductData>> listOfList);

    void onFullfillmentOrderStatusUpdate(List<List<OrderAdapter.RackBoxModel.ProductData>> listOfList);

    List<List<RackAdapter.RackBoxModel.ProductData>> productList();

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList();

    List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn(List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn);

    List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn();

    void onClickRightArrow(RacksDataResponse.FullfillmentDetail fullfillmentDetail);


    //new Callbacks
    void onClickOrderItem(int pos, TransactionHeaderResponse.OMSHeader omsHeader);

    void onClickSalesLine(int position, String status);

    void onClickItemStatusUpdate(int orderAdapterPos, int newSelectedOrderAdapterPos, String status);


    void getBatchDetailsApiCall(GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader);

    void onSuccessGetBatchDetails(GetBatchInfoRes getBatchDetailsResponse, GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader);

    void checkBatchInventorySuccess(String status, CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(String message);

    void onClickOrderAdapterArrow(int pos);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);

    void onClickRackAdapter(int pos);

    void onClickRackItemStart(GetOMSTransactionResponse.SalesLine salesLine);

    void onClickForwardToPacker();

    void onExpansionEshopCharge(TransactionHeaderResponse.OMSHeader omsHeader, int position, int newAdapterposition, GetOMSTransactionResponse.SalesLine salesLine);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> batchList, boolean isRackAdapterClick);

    void onClickOnHold(TransactionHeaderResponse.OMSHeader omsHeader);

    void onClickOnHoldAll();

}
