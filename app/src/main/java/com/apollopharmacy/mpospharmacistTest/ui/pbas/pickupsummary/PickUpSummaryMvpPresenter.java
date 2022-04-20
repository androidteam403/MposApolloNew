package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.ForwardToPickerRequest;

import java.util.List;

public interface PickUpSummaryMvpPresenter<V extends PickUpSummaryMvpView> extends MvpPresenter<V> {
    void forwardtoPacker();

    void setFullfillmentData(List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList);

    List<RacksDataResponse.FullfillmentDetail> getFullFillmentList();
    void ForwardToPickerRequest(ForwardToPickerRequest request);

    void setListOfListFullfillmentData(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullfillmentDetailList);

    List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList();

    void onClickScanCode();
}
