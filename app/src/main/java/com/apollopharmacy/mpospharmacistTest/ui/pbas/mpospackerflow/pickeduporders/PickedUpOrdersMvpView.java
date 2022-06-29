package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.List;

public interface PickedUpOrdersMvpView extends MvpView {
    void startPickUp();

    void onClickScanCode();

    void onItmClick(int position, TransactionHeaderResponse.OMSHeader omsHeader);

    void noOrderFound(int count);
    void onClickFilterIcon();
    void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList);

    void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader);

    void onSuccessGetOMSTransactionList(OMSTransactionResponse response);
//    void onItemClick(int position, String status, List<RackAdapter.RackBoxModel.ProductData> productDataList, List<RacksDataResponse.FullfillmentDetail> fullFillModel, RacksDataResponse.FullfillmentDetail fillModel);
}
