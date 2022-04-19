package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public interface BatchListMvpView extends MvpView {

    void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onCheckBoxClick(int position, boolean batchSelected, double reqqty, String batchNo, String itemID);

    void checkBatchInventorySuccess(CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(CheckBatchInventoryRes body);

    void onAddItemsClicked();
}
