package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;

public interface OrdersMvpPresenter<V extends OrdersMvpView> extends MvpPresenter<V> {

    void getJounalOnlineOrderTransactionApiCall();

    void detailorderServiceCall(OrderListReq orderListReq);

    void onClickSearchTextClear();

    void onClickScanCode();
}
