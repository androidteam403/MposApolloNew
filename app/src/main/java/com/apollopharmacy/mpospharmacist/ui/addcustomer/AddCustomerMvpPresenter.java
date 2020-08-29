package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AddCustomerMvpPresenter<V extends AddCustomerMvpView> extends MvpPresenter<V> {

    void onDateClick();

    void onClickSubmit();

    void onClickAnniversary();

    void onClickRegistration();

    void onActionBarBackPressed();

    void handleCustomerAddService( );

    String getStoreName();

    String getStoreId();

    String getTerminalId();
}
