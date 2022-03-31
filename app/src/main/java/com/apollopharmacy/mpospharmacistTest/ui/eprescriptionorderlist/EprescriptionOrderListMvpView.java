package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model.OMSTransactionHeaderResModel;

public interface EprescriptionOrderListMvpView extends MvpView
{
    void onClickBackPressed();

    void onOrderItemClick(int position, OMSTransactionHeaderResModel.OMSHeaderObj item);

    void onSuccessGetOMSTransactionList(OMSTransactionHeaderResModel response);

    void PickingOrderPressed();

    void PackingOrderPressed();

    void  InvoiceOrderPressed();
}
