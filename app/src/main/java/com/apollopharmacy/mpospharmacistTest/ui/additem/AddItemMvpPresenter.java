package com.apollopharmacy.mpospharmacistTest.ui.additem;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;

import java.util.ArrayList;


public interface AddItemMvpPresenter<V extends AddItemMvpView> extends MvpPresenter<V> {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickBackPressed();


    void onPayButtonClick();

    void onClickCardPayment();

    void onClickHdfcPay();

    void onClickSmsPAyMode();

    void onClickVendorPayMode();

    void onClickCodPayMode();


    void showsmsPaymentDialog();

    void showHdfcPaymentDialog();

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

    void checkProductTrackingWise();

    void changeQuantity(SalesLineEntity salesLineEntity, double quantity);

    void calculatePosTransaction();

    void onClickOTPVerify();

    void onClickReSendOTP();

    void onSuccessCardPayment(double amount);

    void onClickGenerateBill();

    void onClickManualDisc();

    void onClickCouponDisc();

    void toApplyManualDisc(ManualDiscCheckRes body, ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList, String fixedDiscountCode);

    void generateOTP();

    void generateTenterLineService(double amount, WalletServiceRes walletServiceRes);

    void validateOmsOrder(double totalamount, CalculatePosTransactionRes calculatePosTransactionRes, CustomerDataResBean customerDataResBean);

    void clearAllVoidTransaction();

    void closeOrderVoidTransaction();

    void voidProduct(int lineNumber);

    void checkAllowedPaymentMode(PaymentMethodModel paymentMethodModel);

    void onSelectPhonePe();

    void onSelectPhonePeQrMode();

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

    void getPaymentVoidApiCall(CalculatePosTransactionRes calculatePosTransactionRes, PaymentVoidReq.Wallet wallet, int lineNo);

    void circlePlanButtonEvent();

    //void eprescriptionListButtonEvent();

    void CircleplanCashbackapicall();

    void Posttransactionrequest();

    void sendSmsservice(String mobilenumber);

    void omsaddnewitem(ArrayList<SalesLineEntity> itemsArrayList);


    void getUnpostedTransaction();

    void getGlobalConfig();

    void getHBPConfig();

    void checkCustomerExistOrNot(GetCustomerResponse.CustomerEntity mobileNumber);

    void checkCustomerInOneApollo(GetCustomerResponse.CustomerEntity mobileNumber);

    void generateOtp(String mobileNumber);

    void createNewCustomer();
}
