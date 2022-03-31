package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;

import java.util.List;

public interface PickUpSummaryMvpView extends MvpView {
    void forwardtoPacker();

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList();

    List<List<RackAdapter.RackBoxModel.ProductData>> productList();

    String fullCount(String fullCount);

    String partialCount(String partialCount);

    String notAvailable(String notAvailableCount);

    void onClickItem(int pos);
}
