package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CircleMemebershipCashbackPlanResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetPostOnlineOrderApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter.PayAdapterModel;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.ArrayList;

import retrofit2.Response;

public interface AddItemMvpView extends MvpView {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickActionBarBack();

    void onClearAll();

    void onClickClearAllBtn();

    String getPrgTracking();

    void onSuccessClearAll();

    void partialPaymentDialog(String title, String description);

    void onPayButtonClick();

    Context getContext();

    String getCashPaymentAmount();

    String getCardPaymentAmount();

    String getOneApolloPoints();

    String getOneApolloOtp();

    GetCustomerResponse.CustomerEntity getCustomerModule();

    DoctorSearchResModel.DropdownValueBean getDoctorModule();

    CorporateModel.DropdownValueBean getCorporateModule();

    TransactionIDResModel getTransactionModule();

    OrderPriceInfoModel getOrderPriceInfoModel();

    PaymentMethodModel getPaymentMethod();

    ValidatePointsResModel.OneApolloProcessResultEntity getValidateOneApolloPoints();

    ArrayList<SalesLineEntity> getSelectedProducts();

    CalculatePosTransactionRes getCalculatedPosTransactionRes();

    CustomerDataResBean getCustomerDataResbean();

    GetSMSPayAPIResponse getGetSMSPayAPIResponse();

    void setErrorCardPaymentAmountEditText(String message);

    void setErrorCashPaymentAmountEditText(String message);

    void setErrorOneApolloOtpEditText(String message);

    void onClickCardPaymentBtn();

    void onClickHdfcPayBtn();

    void onClickSmsPayBtn();

    void onClickVendorPayBtn();

    void onClickCodPayBtn();

    void onClickCashPaymentBtn();

    void onClickOneApolloBtn();

    void onClickWalletPaymentBtn();

    void onClickEditItemsList();

    void onFailedGenerateTenderLine(GenerateTenderLineRes body);

    void onSuccessSaveRetailTransaction(SaveRetailsTransactionRes body);

    void onSuccessSmsPayTransaction(GetSMSPayAPIResponse body);

    void onFailedSmsPayTransaction(GetSMSPayAPIResponse body);

    void onSuccessSmsPayCancelTransaction(GetSMSPayAPIResponse res);

    void onSuccessPhonepeGenerateQrCode(PhonepeGenerateQrCodeResponse res);

    void onSuccessSmsPayValidateTransaction(GetSMSPayAPIResponse body);

    void onFailedSmsPayValidateTransaction(GetSMSPayAPIResponse body);

    void onFailedSaveRetailsTransaction(SaveRetailsTransactionRes body);

    void onSuccessValidateOneApolloPoints(ValidatePointsResModel body);

    void onFailedValidateOneApolloPoints(ValidatePointsResModel body);

    void onSuccessCheckProductTrackingWise(CalculatePosTransactionRes posTransactionRes);

    void onSuccessCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes);

    void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity);

    void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity);

    void isManualDisc(boolean isManualDisc);

    void onItemDeleted(int lineNumber, SalesLineEntity item);

    void onItemAdded(int lineNumber);

    void onItemEdit(SalesLineEntity item);

    void onClickGenerateBill();

    boolean isDonePayment();

    //boolean isOMSOrder();

    double orderTotalAmount();

    double orderRemainingAmount();

    void updatePayedAmount(CalculatePosTransactionRes posTransactionRes);

    void toRemovePayedAmount(int position, PayActivityAdapter.ViewHolder holder);

    void toAddPayedAmount(PayAdapterModel item, int pos);

    void openManualDiscDialog(ManualDiscCheckRes body);

    void errorMessageDialog(String title, String message);

    void generateOTPResponseSuccess(String otp);

    void clearOTPVIew();

    void showDoctorSelectError();

    void closeOrderSuccess();

    void showOTPPopUp(double amount, String otp);

    void showCreditPayment(double amount, GetTrackingWiseConfing._TrackingConfigrationEntity entity);

    void showCouponCodeDialog(double categoryAmount);

    void corpPrgTrackingError();

    void onUploadApiCall();

    void onFaliureStaffListData(PharmacyStaffApiRes pharmacyStaffApiRes);

    void onSucessStaffListData(PharmacyStaffApiRes res);

    void onItemClick(int position, double quantity, SalesLineEntity salesLineEntity);

    String getSalesOrigin();

    void getWalletResponseData(Response<WalletServiceRes> walletServiceRes);

    void onSuccessPaymentVoidData(PaymentVoidRes paymentVoidRes);

    void onCircleplanButtonevent();

    //void onEprescriptionButtonevent();

    void CirclecashbackplanSuccess(CircleMemebershipCashbackPlanResponse response);

    void CirclecashbackplanFailure(CircleMemebershipCashbackPlanResponse response);

    ArrayList<CircleMemebershipCashbackPlanResponse.Category> getCirclecashbackplan();

    void Posttratransactionrequest(POSTransactionEntity entity);

    void onSucessOMSOrderValidate(CalculatePosTransactionRes calculatePosTransactionRes);

    void omsremainamount(double remainamount);

    void generateotpSuccess(ModelMobileNumVerify response, String otp);

    void showOTPPopUpForOMSOrder(String otp);


    void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body);

    void onSuccessOmsAddNewItem(OmsAddNewItemResponse body, ArrayList<SalesLineEntity> itemsArrayList);

    void onFailedOmsAddNewItem(OmsAddNewItemResponse body);

    void onSuccessHdfcPaymentListGenerateApi(HdfcLinkGenerateResponse hdfcLinkGenerateResponse);// changes made by naveen

    void onFailureHdfcPaymentListGenerateApi(HdfcLinkGenerateResponse hdfcLinkGenerateResponse);// changes made by naveen

    String getHdfcTransactionId();// changes made by naveen

    void getGlobalConfig(GetGlobalConfingRes getGlobalConfingRes);

    void getHBPConfig(HBPConfigResponse hbpConfigResponse);

    void showOTPDialog(String otp);

    void addCustomerFailed(String errMsg);

    boolean isOnleneOrder();

    void onSuccessGetPostOnlineOrderApi(GetPostOnlineOrderApiResponse getPostOnlineOrderApiResponse);

    void onFailedGetPostOnlineOrderApi(GetPostOnlineOrderApiResponse getPostOnlineOrderApiResponse);

    void noStockAvailableClearAll();

    String getOnlineTransactionId();

    CalculatePosTransactionRes getUnPostedTransactionResponseBody();

    Boolean isCameFromOrderDetailsScreenActivity();

    void SuccessOnlineorderCorporatelist(GetOnlineCorporateListApiResponse response, double amount);

    String getPatientType();
}
