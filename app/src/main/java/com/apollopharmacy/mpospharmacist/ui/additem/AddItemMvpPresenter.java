package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.TenderLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineRes;

import java.util.ArrayList;

public interface AddItemMvpPresenter<V extends AddItemMvpView> extends MvpPresenter<V> {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickBackPressed();

    void onClickClearAllBtn();

    void onPayButtonClick();

    void onClickCardPayment();

    void onClickCashPayment();

    void onClickOneApolloPayment();

    void onClickCardPaymentPay();

    void onClickCashPaymentPay();

    void onClickOneApolloPaymentPay();

    void onClickEditItemsList();

    void validateOneApolloPoints(String userMobileNumber, String transactionID);

    void onClickRedeemPoints();

    void calculatePosTransaction();

    boolean validateOneApolloPoints();

    void onClickOTPVerify();

    void onClickReSendOTP();

    void onSuccessCardPayment(double amount);

    void onClickGenerateBill();

    void getTenderTypeApi();

    void onClickManualDisc();

    void toApplyManualDisc(ManualDiscCheckRes body, ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList, String fixedDiscountCode);

    void generateOTP();

    void validateOTP();



    void generateTenterLineService(double amount);
}
