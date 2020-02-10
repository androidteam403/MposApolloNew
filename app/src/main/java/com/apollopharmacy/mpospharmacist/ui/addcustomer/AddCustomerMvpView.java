package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface AddCustomerMvpView extends MvpView {

    void onDateClick();

    void onSubmitClick();

    void onAnniversaryClick();

    void onRegistrationClick();

    void onClickBackPressed();
}
