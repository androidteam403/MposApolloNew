package com.apollopharmacy.mpospharmacistTest.ui.circleplan;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;

public interface CirclePlanCashbackMvpView extends MvpView {
    void onClickBackBtn();

    void onCintinueBtn();

    void onSuccessCircleplanDetails(CircleplanDetailsResponse response);

    void onFailureCircleplanDetails(CircleplanDetailsResponse response);

    void planonecheckboxaction();

    void plantwocheckboxaction();

    void ernollbuttonaction();

    void ernollexitbuttonaction();

    void generateotpSuccess(ModelMobileNumVerify response, String otp);

   void generateotpFailed(String errMsg);

   void CheckBatchStockSuccess(CalculatePosTransactionRes response);

    void CheckBatchStockFailure(CalculatePosTransactionRes response);

    void circleplantransactionSuccess(CalculatePosTransactionRes response,String corpcode);

    void circleplantransactionFailure(CalculatePosTransactionRes response);

   // void getcircleplan();


}
