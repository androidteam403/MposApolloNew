package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.util.List;

public interface StockAvailableOrdersMvpPresenter<V extends StockAvailableOrdersMvpView> extends MvpPresenter<V> {
    void onClickBack();

    void mposPickPackOrderReservationApiCall(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList, int requestType, String user);

    List<UserModel._DropdownValueBean> getLoginUserDetails();

    void getOmsTransactionHeader();
}
