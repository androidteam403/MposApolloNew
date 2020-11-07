package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface OrderSummaryMvpView extends MvpView {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    SaveRetailsTransactionRes transResData();
}
