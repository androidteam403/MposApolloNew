package com.apollopharmacy.mpospharmacistTest.ui.home;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;

public interface MainActivityMvpView extends MvpView {

    void navigateLoginActivity();

    void getCorporateList(CorporateModel corporateModel);

    void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body);

    Context getContext();
}
