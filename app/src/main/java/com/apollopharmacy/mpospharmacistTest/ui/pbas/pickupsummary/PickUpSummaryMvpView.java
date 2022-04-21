package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.ForwardToPickerResponse;

import java.util.List;

public interface PickUpSummaryMvpView extends MvpView {
    void forwardtoPacker();

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList();

    List<List<RackAdapter.RackBoxModel.ProductData>> productList();

    String fullCount(String fullCount);
    void OmsOrderUpdateSuccess(ForwardToPickerResponse response);
    void OmsOrderUpdateFailure(ForwardToPickerResponse response);
    String partialCount(String partialCount);
    void Forward_To_Pickerconfirmation();

    String notAvailable(String notAvailableCount);

    void onClickItem(int pos);

    void onClickScanCode();
}
