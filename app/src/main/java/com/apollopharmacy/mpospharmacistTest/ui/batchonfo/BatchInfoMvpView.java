package com.apollopharmacy.mpospharmacistTest.ui.batchonfo;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.expirymodel.ExpiryChangeRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public interface BatchInfoMvpView extends MvpView {
    void onIncrementClick();

    void onDecrementClick();

    void onNavigateNextActivity();

    void onClickBackPressed();

    void onSuccessBatchInfo(GetBatchInfoRes body);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj);

    void onItemExpiryClick(int position, int quantity);

    String getRequiredQuantity();

    void checkBatchInventorySuccess(boolean isAlertDialog);

    void checkBatchInventoryFailed(String returnMessage);

    void batchItemInfo(GetBatchInfoRes.BatchListObj batchListObj);

    List<GetBatchInfoRes.BatchListObj> getbatchInfoRes();

    void checkExpiryDateChangeSuccess(ExpiryChangeRes expiryChangeRes);

    void onSucessPlayList();

    Context getContext();
}
