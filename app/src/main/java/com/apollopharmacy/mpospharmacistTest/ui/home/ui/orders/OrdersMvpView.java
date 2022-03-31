package com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

import java.util.ArrayList;

public interface OrdersMvpView extends MvpView {

    void onItemClick(CalculatePosTransactionRes item);

    void onSuccessOrderList(ArrayList<CalculatePosTransactionRes> orderListRes);

    void onSuccessdetailOrderList(ArrayList<CalculatePosTransactionRes> orderListRes);

    void noDataFound();

    void onClickApplyFilters();

    void onSucessPlayList();

    Context getContext();

}
