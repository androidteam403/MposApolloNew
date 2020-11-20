package com.apollopharmacy.mpospharmacist.ui.home;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;

public interface MainActivityMvpView extends MvpView {

    void navigateLoginActivity();

    void displayAppInfoDialog(String title,String subTitle,String positiveBtn,String negativeBtn);

    void getCorporateList(CorporateModel corporateModel);

    void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body);

    void onSucessPlayList();

    Context getContext();
}
