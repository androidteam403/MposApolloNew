package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

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

    void onSuccessCardPayment(String response);

    void onClickGenerateBill();

    void getTenderTypeApi();

    void onClickManualDisc();

    void toApplyManualDisc(ManualDiscCheckRes body, ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList, String fixedDiscountCode);
}
