package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface BillerOrdersMvpPresenter<V extends BillerOrdersMvpView> extends MvpPresenter<V> {
    void onClickFilterIcon();
    void fetchFulfilmentOrderList();
    void onclickScanCode();
    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();
    void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);
    void onRackApiCall();
}
