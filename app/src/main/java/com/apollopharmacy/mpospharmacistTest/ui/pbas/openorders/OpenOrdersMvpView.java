package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface OpenOrdersMvpView extends MvpView {
    void onFullfillmentItemClick(int pos, int itemPos, TransactionHeaderResponse.OMSHeader omsHeader);

    void onRightArrowClickedContinue(int pos);

    void onClickContinue();

    void onSuccessRackApi(RacksDataResponse body);

    void onClickFilterIcon();

    void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader);

    void onClickScanCode();


    void onStockAvailability();

    void onClickItem();


    void onClickStausIcon(int fullFillmentPos, int pos);

    void ondownArrowClicked(String refId, int position);

    void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body);

    void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList);

    void noOrderFound(int count);

    void setFiltersHeaderLists(List<TransactionHeaderResponse.OMSHeader> omsHeaderListlu, boolean isRefresh);

    void onClickPrevPage();

    void onClickNextPage();

    void onClickPrescriptionPreview(GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList, int position);

    void onSuccessGetOmsTransactionAutoAssign(List<GetOMSTransactionResponse> getOMSTransactionResponseList);
}
