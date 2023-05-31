package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;

public interface StockInwardProcessMvpView extends MvpView {
    void onClickFromDate();

    void onClickToDate();

    void onClickItem(int position);

    void onSuccessgetInventoryTransactionDetails(GetInventoryTransactionDetailsResponse body);

    void onClickShow();
}
