package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.List;

public interface OnHoldMvpPresenter<V extends OnHoldMvpView> extends MvpPresenter<V> {

    void getOMSTransactionHeaderApiCall();

    void onClickScanCode();

    void onClickSearchClear();

    void onClickUnHold(TransactionHeaderResponse.OMSHeader omsHeader);

    void mposPickPackOrderReservationApiCall(TransactionHeaderResponse.OMSHeader omsHeader);

    void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> omsHeaderList);

    void onClickFilterIcon();

    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();
}
