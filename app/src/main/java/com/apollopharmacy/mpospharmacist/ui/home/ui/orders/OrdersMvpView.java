package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

import java.util.ArrayList;

public interface OrdersMvpView extends MvpView {

    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick(CalculatePosTransactionRes item);

    String getSearchMobileNumber();

    void setErrorMessageEditText(String message);

    void onSuccessOrderList(ArrayList<CalculatePosTransactionRes> orderListRes);

    void noDataFound();

    void onClickApplyFilters();
}
