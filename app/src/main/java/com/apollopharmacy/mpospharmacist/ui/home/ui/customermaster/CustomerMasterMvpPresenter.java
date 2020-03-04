package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpView;

public interface CustomerMasterMvpPresenter<V extends CustomerMasterMvpView> extends MvpPresenter<V> {
    void onDateClick();

    void onClickSubmit();

    void onClickAnniversary();

    void onClickRegistration();

    void onActionBarBackPressed();

    void handleCustomerAddService( );
}
