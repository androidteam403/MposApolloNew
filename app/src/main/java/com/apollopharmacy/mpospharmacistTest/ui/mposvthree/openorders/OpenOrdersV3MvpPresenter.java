package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.util.List;

public interface OpenOrdersV3MvpPresenter<V extends OpenOrdersV3MvpView> extends MvpPresenter<V> {
    void fetchFulfilmentOrderList(boolean isRefresh);

    List<TransactionHeaderResponse.OMSHeader> getGlobalTotalOmsHeaderList();

    void onClickScanCode();

    void onClickPrevPage();

    void onClickNextPage();

    void onClickContinue();

    void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);

    String getUserId();

    List<UserModel._DropdownValueBean> getMaxMinOrdersList();

    void mposPickPackOrderReservationApiCall(int requestStatus, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList);

    void onGetOmsTransaction(String fulfilmentId, boolean isItemClick, boolean isAutoAssign, boolean isBulkSelection);

    void setGlobalTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList);

    GetGlobalConfingRes getGlobalConfiguration();

    List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList();
}
