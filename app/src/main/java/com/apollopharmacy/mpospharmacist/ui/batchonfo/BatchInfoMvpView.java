package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;

public interface BatchInfoMvpView extends MvpView {
    void onIncrementClick();

    void onDecrementClick();

    void onNavigateNextActivity();

    void onClickBackPressed();

    void onSuccessBatchInfo(GetBatchInfoRes body);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onItemClick(int position);

    String getRequiredQuantity();
}
