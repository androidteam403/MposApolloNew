package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

public interface OpenOrdersMvpPresenter<V extends OpenOrdersMvpView> extends MvpPresenter<V> {
    void onClickContinue();

    void onRackApiCall();

    void onClickFilterIcon();

    void fetchFulfilmentOrderList();

    void onClickScanCode();

    void onGetOmsTransaction(String fulfilmentId, boolean isItemClick);

}
