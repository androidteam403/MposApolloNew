package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

import java.util.List;

public interface OpenOrdersMvpPresenter<V extends OpenOrdersMvpView> extends MvpPresenter<V> {
    void onClickContinue();

    void onRackApiCall();

    void onClickFilterIcon();

    void fetchFulfilmentOrderList(boolean isRefresh);

    void onClickScanCode();


    void onGetOmsTransaction(String fulfilmentId, boolean isItemClick);

    void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);

    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();

    GetGlobalConfingRes getGlobalConfiguration();

    String getUserId();

    void setGlobalTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);

    List<TransactionHeaderResponse.OMSHeader> getGlobalTotalOmsHeaderList();

    void onClickPrevPage();

    void onClickNextPage();

    String getTerminalId();
}
