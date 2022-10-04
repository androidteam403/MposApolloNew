package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public interface BatchListMvpView extends MvpView {

    void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onCheckBoxClick(GetBatchInfoRes.BatchListObj item, int position, double reservedqty);

    void onUncheckBoxClick(GetBatchInfoRes.BatchListObj batchListModel, double reqqty);

    void checkBatchInventorySuccess(CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(CheckBatchInventoryRes body);

    void onAddItemsClicked();
    void onClickNotify();

    void noOrderFound(int count);

    void onNavigateNextActivity();

    void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj);

    void onClickSelectedBatch(GetBatchInfoRes.BatchListObj batchListModel);

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();

    void onClickAutoUpdate();

    void checkBatchInventorySuccess(String status, CheckBatchInventoryRes body);

    void checkBatchInventoryFailed(String message);

    void onClickBack();

    void onClickNotAvailableBtn();
}
