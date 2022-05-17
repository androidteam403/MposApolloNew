package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface PickedUpOrdersMvpPresenter<V extends PickedUpOrdersMvpView> extends MvpPresenter<V> {
    void startPickUp();

    void onClickScanCode();
    void fetchOMSOrderList();
    List<RacksDataResponse.FullfillmentDetail> getFullFillmentList();

    List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList();

    void setFullFillmentDataList(List<RacksDataResponse.FullfillmentDetail> fullFillmentDataList);

    void setListOfListFullFillProducts(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullFillProducts);

}
