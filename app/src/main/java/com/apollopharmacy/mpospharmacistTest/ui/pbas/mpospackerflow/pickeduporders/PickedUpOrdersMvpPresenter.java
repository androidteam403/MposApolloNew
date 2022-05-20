package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface PickedUpOrdersMvpPresenter<V extends PickedUpOrdersMvpView> extends MvpPresenter<V> {
    void startPickUp();

    void onClickScanCode();
    void fetchOMSOrderList();
    void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);

    List<RacksDataResponse.FullfillmentDetail> getFullFillmentList();
    void onClickFilter();
    List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList();
    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();
    void setFullFillmentDataList(List<RacksDataResponse.FullfillmentDetail> fullFillmentDataList);

    void setListOfListFullFillProducts(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullFillProducts);


    // created by naveen
    void fetchFulfilmentOrderList();

}
