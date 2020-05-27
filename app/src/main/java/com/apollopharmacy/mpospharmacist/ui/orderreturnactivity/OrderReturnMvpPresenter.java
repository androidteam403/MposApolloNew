package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface OrderReturnMvpPresenter<V extends OrederReturnMvpView> extends MvpPresenter<V> {

    void onClickBackPressed();

    void trackingWiseReturnAllowed(String corpId);

    void cancelDSBilling(CalculatePosTransactionRes posTransactionRes);

    void onReturnClick(CalculatePosTransactionRes posTransactionRes);

    void onCancelCLick(CalculatePosTransactionRes posTransactionRes);

    void onReOrderClick(CalculatePosTransactionRes posTransactionRes);

    void orderReturnAll(CalculatePosTransactionRes posTransactionRes);
}
