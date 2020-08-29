package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.WalletServiceRes;
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

    void onClickWalletPayment();

    void onClickCreditPayment();

    void onClickCardPaymentPay();

    void onClickCashPaymentPay();

    void onClickCreditPaymentPay();

    void onClickOneApolloPaymentPay();

    void onClickEditItemsList();

    void validateOneApolloPoints(String userMobileNumber, String transactionID);

    void onClickRedeemPoints();

    void checkProductTrackingWise();

    void changeQuantity(SalesLineEntity salesLineEntity, int quantity);

    void calculatePosTransaction();

    boolean validateOneApolloPoints();

    void onClickOTPVerify();

    void onClickReSendOTP();

    void onSuccessCardPayment(double amount);

    void onClickGenerateBill();

    void getTenderTypeApi();

    void onClickManualDisc();

    void onClickCouponDisc();

    void toApplyManualDisc(ManualDiscCheckRes body, ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList, String fixedDiscountCode);

    void generateOTP();

    void validateOTP();

    CalculatePosTransactionRes getTenderLineEntities();

    void generateTenterLineService(double amount, WalletServiceRes walletServiceRes);

    void clearAllVoidTransaction();

    void closeOrderVoidTransaction();

    void voidProduct(int lineNumber);

    void checkAllowedPaymentMode(PaymentMethodModel paymentMethodModel);

    void onSelectPhonePe();

    void onSelectPayTm();

    void onSelectAirtelMoney();

    boolean validTenderLimit(double amount, String tenderName);

    void getPharmacyStaffApiDetails(String otp, String action, double amount);

    void applyCouponCodeApi(String couponCode, double categoryAmount);

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void onUploadApiCall();

    void getUplaodPharmacyStaffApiDetails(String action);

}
