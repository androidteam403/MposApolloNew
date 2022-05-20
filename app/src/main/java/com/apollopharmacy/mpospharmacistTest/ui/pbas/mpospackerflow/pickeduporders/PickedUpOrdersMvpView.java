package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;



import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface PickedUpOrdersMvpView extends MvpView {
    void startPickUp();
    void onClickScanCode();
    void onItmClick(int position,List<OMSTransactionResponse.OMSHeaderObj> omsHeaderObjList );
    void noOrderFound(int count);
    void onSuccessGetOMSTransactionList(OMSTransactionResponse response);
//    void onItemClick(int position, String status, List<RackAdapter.RackBoxModel.ProductData> productDataList, List<RacksDataResponse.FullfillmentDetail> fullFillModel, RacksDataResponse.FullfillmentDetail fillModel);
}
