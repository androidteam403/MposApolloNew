package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface EprescriptionOrderListMvpPresenter<V extends EprescriptionOrderListMvpView> extends MvpPresenter<V>
{
    void onActionBarBackPressed();

    void fetchOMSOrderList();

    void onClickPickingbutton();

    void onClickPackingbutton();

    void onClickInvoicebutton();
}
