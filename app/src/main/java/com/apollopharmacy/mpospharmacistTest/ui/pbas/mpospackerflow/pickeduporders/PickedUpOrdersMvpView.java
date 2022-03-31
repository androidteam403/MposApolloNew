package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;



import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface PickedUpOrdersMvpView extends MvpView {
    void startPickUp();
    void onClickScanCode();
    void onItemClick(int position, String status, List<RackAdapter.RackBoxModel.ProductData> productDataList, List<RacksDataResponse.FullfillmentDetail> fullFillModel, RacksDataResponse.FullfillmentDetail fillModel);
}
