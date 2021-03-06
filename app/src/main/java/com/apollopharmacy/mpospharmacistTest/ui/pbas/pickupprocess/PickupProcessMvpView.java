package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;

import android.widget.Spinner;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface PickupProcessMvpView extends MvpView {

    void onClickBack();

    void onClickContinue();


    void onClickFullPicked();

    void onClickStausIcon();

    void onClickBatchDetails();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();


    void onClickDropDown(Spinner spinner);

    void onSuccessRackApi(RacksDataResponse racksDataResponse);

    void onRackStatusUpdate(List<List<RackAdapter.RackBoxModel.ProductData>> listOfList);

    void onFullfillmentOrderStatusUpdate(List<List<OrderAdapter.RackBoxModel.ProductData>> listOfList);

    List<List<RackAdapter.RackBoxModel.ProductData>> productList();

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList();

    List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn(List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn);

    List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn();

    void onClickRightArrow(RacksDataResponse.FullfillmentDetail fullfillmentDetail);

}
