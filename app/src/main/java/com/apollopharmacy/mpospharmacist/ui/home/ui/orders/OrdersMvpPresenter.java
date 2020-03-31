package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

public interface OrdersMvpPresenter<V extends OrdersMvpView> extends MvpPresenter<V> {
    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick(CalculatePosTransactionRes item);

    void getOrdersDetails();

    void orderServiceCall(OrderListReq orderListReq);
}
