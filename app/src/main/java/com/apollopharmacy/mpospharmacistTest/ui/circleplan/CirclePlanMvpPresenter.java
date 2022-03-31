package com.apollopharmacy.mpospharmacistTest.ui.circleplan;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsRequest;

public interface CirclePlanMvpPresenter<V extends CirclePlanCashbackMvpView> extends MvpPresenter<V> {
    void onClickBackPress();

    void onContinueButtonpressed();

    void circleplandetailsapicall(CircleplanDetailsRequest request);

    void planonecheckboxpressed();

    void plantwocheckboxpressed();

    void clickonernollbuttonpressed();

    void clickonernollexitbuttonpressed();

    void  sendSmsservice(String mobilenumber);

    void oncheckbatchstock(POSTransactionEntity posTransactionEntity, String circleprice);

    void circlrplantransaction(String price,CalculatePosTransactionRes calculatePosTransactionRes);

  //  void getcircleplan_function();
     //void getcircleplan();
}
