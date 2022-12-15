package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;

import java.util.List;

public interface BillerOrdersMvpPresenter<V extends BillerOrdersMvpView> extends MvpPresenter<V> {
    void onClickFilterIcon();

    void fetchFulfilmentOrderList();

    void onclickScanCode();

    List<OMSTransactionHeaderResModel.OMSHeaderObj> getTotalOmsHeaderList();

    void setTotalOmsHeaderList(List<OMSTransactionHeaderResModel.OMSHeaderObj> totalOmsHeaderList);

    void onRackApiCall();

    void mposPickPackOrderReservationApiCall(OMSTransactionHeaderResModel.OMSHeaderObj omsHeaderObj);

    String getTerminalId();
}
