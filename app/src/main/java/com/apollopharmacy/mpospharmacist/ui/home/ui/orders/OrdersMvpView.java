package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

import java.util.ArrayList;

public interface OrdersMvpView extends MvpView {

    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick();

    String getSearchMobileNumber();

    void setErrorMessageEditText(String message);

    void onSuccessOrderList(ArrayList<OrderListRes> orderListRes);

    void noDataFound();
}
