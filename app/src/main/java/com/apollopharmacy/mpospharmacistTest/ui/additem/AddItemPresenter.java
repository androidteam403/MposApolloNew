package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ExitInfoDialogBinding;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.dialog.HdfcPaymentDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CircleMemebershipCashbackPlanResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CouponDiscount;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetPostOnlineOrderApiRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetPostOnlineOrderApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OTPRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffAPIReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SendGlobalMessageRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SendGlobalMessageResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.TenderLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.TypeEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.Wallet;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiRequest;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerRequest;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.eze.api.EzeAPI;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemPresenter<V extends AddItemMvpView> extends BasePresenter<V> implements AddItemMvpPresenter<V> {
    private final int REQUEST_CODE_INITIALIZE = 10001;

    @Inject
    public AddItemPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onManualSearchClick() {
        getMvpView().onManualSearchClick();
    }

    @Override
    public void circlePlanButtonEvent() {
        getMvpView().onCircleplanButtonevent();
    }

    /*@Override
    public void eprescriptionListButtonEvent() {
        getMvpView().onEprescriptionButtonevent();
    }*/

    @Override
    public void onVoiceSearchClick() {
        getMvpView().onVoiceSearchClick();
    }

    @Override
    public void onBarCodeSearchClick() {
        getMvpView().onBarCodeSearchClick();
    }

    @Override
    public void onClickBackPressed() {
        getMvpView().onClickActionBarBack();
    }


    //unposted transaction.....
    @Override
    public void getUnpostedTransaction() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.GET_UNPOSTED_TRANSACTION(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if (response.body().getSalesLine() != null && response.body().getSalesLine().size() > 0) {
                                getMvpView().onSuccessGetUnPostedPOSTransaction(response.body());
                            } else {
                                getMvpView().hideLoading();
                                getMvpView().noStockAvailableClearAll();
                            }
                        } else getMvpView().hideLoading();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getGlobalConfig() {
        getMvpView().getGlobalConfig(getDataManager().getGlobalJson());
    }

    @Override
    public GetGlobalConfingRes getGlobalConfiguration() {
        return getDataManager().getGlobalJson();
    }

    @Override
    public void getHBPConfig() {
        if (getDataManager().getGlobalJson().isISHBPStore())
            getMvpView().getHBPConfig(getDataManager().getHBPConfigRes());
    }

    @Override
    public void checkCustomerExistOrNot(GetCustomerResponse.CustomerEntity mobilenumber) {
        if (!TextUtils.isEmpty(mobilenumber.getMobileNo())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                GetCustomerRequest customerRequest = new GetCustomerRequest();
                customerRequest.setSearchString(mobilenumber.getMobileNo());
                customerRequest.setSearchType(0);
                customerRequest.setISAX(true);
                customerRequest.setISOneApollo(true);
                customerRequest.setStore(getDataManager().getStoreId());
                customerRequest.setTerminal(null);
                customerRequest.setDataAreaID(getDataManager().getDataAreaId());
                customerRequest.setClusterCode(getDataManager().getGlobalJson().getClusterCode());
                customerRequest.setCPEnquiry(true);
                customerRequest.setOneApolloSearchUrl(getDataManager().getGlobalJson().getCustomerSearchOneApolloUrl());
                customerRequest.setAXSearchUrl(getDataManager().getGlobalJson().getCustomerSearchAXUrl());
                Call<GetCustomerResponse> call = api.GET_CUSTOMER_REQUEST_CALL(customerRequest);
                call.enqueue(new Callback<GetCustomerResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetCustomerResponse> call, @NotNull Response<GetCustomerResponse> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                            if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 1) {
                                checkCustomerInOneApollo(mobilenumber);
                            } else {
                                getMvpView().getCustomerModule().setExistingCustomerOrNot(true);
                                if (getMvpView().getCustomerModule().isCardPayment()) {
                                    onClickCardPayment();
                                } else {
                                    onClickCashPaymentPay();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetCustomerResponse> call, @NotNull Throwable t) {
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

    @Override
    public void checkCustomerInOneApollo(GetCustomerResponse.CustomerEntity mobileNumber) {
        if (!TextUtils.isEmpty(mobileNumber.getMobileNo())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                ValidatePointsReqModel pointsReqModel = new ValidatePointsReqModel();
                ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
                requestDataEntity.setStoreId("");
                requestDataEntity.setDocNum("");
                requestDataEntity.setMobileNum(mobileNumber.getMobileNo());
                requestDataEntity.setReqBy("M");
                requestDataEntity.setRRNO("");
                requestDataEntity.setOTP("");
                requestDataEntity.setAction("BALANCECHECK");
                requestDataEntity.setCoupon("");
                requestDataEntity.setType("");
                requestDataEntity.setCustomerID("");
                requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
                pointsReqModel.setRequestData(requestDataEntity);
                Call<ValidatePointsResModel> call = api.VALIDATE_ONE_APOLLO_POINTS(pointsReqModel);
                call.enqueue(new Callback<ValidatePointsResModel>() {
                    @Override
                    public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
                        if (response.isSuccessful()) {
                            getMvpView().hideLoading();
                            if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                                if (response.body().getOneApolloProcessResult().getStatus().equalsIgnoreCase("False")) {
//                                    mobileNumber.setExistingCustomerOrNot(true);
                                    generateOtp(getMvpView().getCustomerModule().getMobileNo());
                                } else {
                                    getMvpView().getCustomerModule().setExistingCustomerOrNot(true);
                                    if (getMvpView().getCustomerModule().isCardPayment()) {
                                        onClickCardPayment();
                                    } else {
                                        onClickCashPaymentPay();
                                    }
                                }
                            } else {
                                generateOtp(getMvpView().getCustomerModule().getMobileNo());
//                                getMvpView().getCustomerModule().setExistingCustomerOrNot(true);
//                                if (getMvpView().getCustomerModule().isCardPayment()) {
//                                    onClickCardPayment();
//                                } else {
//                                    onClickCashPaymentPay();
//                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

    @Override
    public void generateOtp(String mobileNumber) {
        if (!TextUtils.isEmpty(mobileNumber)) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                SendGlobalMessageRequest sendGlobalMessageRequest = new SendGlobalMessageRequest();
                sendGlobalMessageRequest.setMOBILENO(mobileNumber);
                sendGlobalMessageRequest.setSOURCEFOR("DEFAULT");
                sendGlobalMessageRequest.setTYPE("OTP");
                sendGlobalMessageRequest.setURL(getDataManager().getGlobalJson().getSendGlobalMessageURL());
                Call<SendGlobalMessageResponse> call = api.SEND_GLOBAL_MESSAGE_RESPONSE_CALL(sendGlobalMessageRequest);
                call.enqueue(new Callback<SendGlobalMessageResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<SendGlobalMessageResponse> call, @NotNull Response<SendGlobalMessageResponse> response) {
                        if (response.isSuccessful()) {
                            getMvpView().hideLoading();
                            if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                                if (!TextUtils.isEmpty(response.body().getOtp())) {
                                    getMvpView().showOTPDialog(response.body().getOtp());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<SendGlobalMessageResponse> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

    @Override
    public void createNewCustomer() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            AddCustomerReqModel addCustomerReqModel = new AddCustomerReqModel();
            addCustomerReqModel.setFirstName(getMvpView().getCustomerModule().getCardName());
            // addCustomerReqModel.setMiddleName(getMvpView().getMiddleName());
            // addCustomerReqModel.setLastName(getMvpView().getLastName());
            addCustomerReqModel.setMiddleName("");
            addCustomerReqModel.setLastName("");
            addCustomerReqModel.setPostalAddress(getMvpView().getCustomerModule().getPostalAddress());
            //addCustomerReqModel.setCity(getMvpView().getCityOption());
            addCustomerReqModel.setCity("");

            addCustomerReqModel.setState(getMvpView().getCustomerModule().getState());
            // addCustomerReqModel.setDistrict(getMvpView().getDistrictOption());
            addCustomerReqModel.setDistrict("");
            addCustomerReqModel.setZipCode(getMvpView().getCustomerModule().getZipCode());
            //addCustomerReqModel.setEmail(getMvpView().getEmail());
            addCustomerReqModel.setEmail("");
            // addCustomerReqModel.setTelephone(getMvpView().getTelephone());
            addCustomerReqModel.setTelephone("");
            addCustomerReqModel.setDataAreaId(getDataManager().getDataAreaId());
            addCustomerReqModel.setMobile(getMvpView().getCustomerModule().getMobileNo());
//            addCustomerReqModel.setDOB(getMvpView().getCustomerModule().getDob());
//            addCustomerReqModel.setDOA("");
            //addCustomerReqModel.setAge(getMvpView().getAge());
            addCustomerReqModel.setGender(getMvpView().getCustomerModule().getGender());
            // addCustomerReqModel.setMaritalStatus(getMvpView().getMaritalStatus());
            addCustomerReqModel.setMaritalStatus("");
            // addCustomerReqModel.setDependentsNo(getMvpView().getNumberOfDependants());
            addCustomerReqModel.setDependentsNo(0);
            addCustomerReqModel.setCardNumber(getMvpView().getCustomerModule().getCardNo());
            String timeStamp = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(Calendar.getInstance().getTime());
            addCustomerReqModel.setRegistrationDate(timeStamp);
            addCustomerReqModel.setCorpId("");
            addCustomerReqModel.setCustId("");
            addCustomerReqModel.setStoreId(getDataManager().getStoreId());
            addCustomerReqModel.setAXDomain(getDataManager().getGlobalJson().getAXServiceDomain());
            addCustomerReqModel.setAXUserId(getDataManager().getGlobalJson().getAXServiceUsername());
            addCustomerReqModel.setAXPassword(getDataManager().getGlobalJson().getAXServicePassword());
            addCustomerReqModel.setCustomerCreationURL(getDataManager().getGlobalJson().getAXServiceURL());
            addCustomerReqModel.setRequestStatus(getDataManager().getGlobalJson().getRequestStatus());
            addCustomerReqModel.setReturnMessage(getDataManager().getGlobalJson().getReturnMessage());
            Call<AddCustomerResModel> call = api.ADD_CUSTOMER_SERVICE(addCustomerReqModel);
            call.enqueue(new Callback<AddCustomerResModel>() {
                @Override
                public void onResponse(@NotNull Call<AddCustomerResModel> call, @NotNull Response<AddCustomerResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().getCustomerModule().setExistingCustomerOrNot(true);
                            if (getMvpView().getCustomerModule().isCardPayment()) {
                                onClickCardPayment();
                            } else {
                                onClickCashPaymentPay();
                            }
                        } else {
                            if (response.body().getReturnMessage().contains("THE RECORD ALREADY EXISTS")) {
                                getMvpView().getCustomerModule().setExistingCustomerOrNot(true);
                                if (getMvpView().getCustomerModule().isCardPayment()) {
                                    onClickCardPayment();
                                } else {
                                    onClickCashPaymentPay();
                                }
                            } else {
                                getMvpView().addCustomerFailed(response.body().getReturnMessage());
                            }

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddCustomerResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onPayButtonClick() {
        getMvpView().hideKeyboard();
        getMvpView().onPayButtonClick();
    }

    @Override
    public void onClickCardPayment() {
        getMvpView().getCustomerModule().setCardPayment(true);
        if (!getMvpView().getCustomerModule().isExistingCustomerOrNot()) {
            if (!getMvpView().isOnleneOrder() && getDataManager().getGlobalJson().isISHBPStore()) {
                if (getDataManager().getGlobalJson().isISOneApolloCardCreationAllowed()) {
                    checkCustomerExistOrNot(getMvpView().getCustomerModule());
                } else {
                    doPayment(2);
                }
            } else {
                doPayment(2);
            }
        } else {
            doPayment(2);
        }
    }

    @Override
    public void onClickHdfcPay() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(9);
    }

    @Override
    public void onClickCashPayment() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(1);
    }

    @Override
    public void onClickOneApolloPayment() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(3);
    }

    @Override
    public void onClickWalletPayment() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(4);
    }

    @Override
    public void onClickCreditPayment() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(5);
    }


    @Override
    public void sendSmsservice(String mobilenumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            String URl = getDataManager().getGlobalJson().getSMSAPI();

            //randaom Otp Number---->
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            // System.out.println(formatted);
            String message = "Your OTP is " + formatted + ". To proceed with the transaction, please visit www.oneapollo.com and consent to our privacy policies and terms and conditions.";

            String[] separated = URl.split("SendSmsFortemplete?");
            String url = separated[0];
            // url=url+"SendSmsFortemplete?"+"to="+mobilenumber+"&message="+message;
            //  Log.d("Mobile number-->", formatted);
            // Log.d("Mobile number-->", url);

            ApiInterface api = ApiClient.getApiServiceOTp(url);
            Call<ModelMobileNumVerify> call = api.verifyMobileNumber(mobilenumber, message);
            call.enqueue(new Callback<ModelMobileNumVerify>() {
                @Override
                public void onResponse(@NotNull Call<ModelMobileNumVerify> call, @NotNull Response<ModelMobileNumVerify> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        getMvpView().generateotpSuccess(response.body(), formatted);


                    }
                }

                @Override
                public void onFailure(@NotNull Call<ModelMobileNumVerify> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }

    //Thes changes made by Gopal on 09-01-2021
    @Override
    public void onClickSmsPAyMode() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(6);
    }

    @Override
    public void onClickVendorPayMode() {
        if (Singletone.getInstance().itemsArrayList != null && Singletone.getInstance().itemsArrayList.size() > 0) {

            GetGlobalConfingRes getGlobalConfingRes = getGlobalConfiguration();
            boolean isAlloMSOrderDeliveryItem = false;
            for (int o = 0; o < Singletone.getInstance().itemsArrayList.size(); o++) {
                boolean isAlloMSOrderDeliveryItemOne = false;
                if (!Singletone.getInstance().itemsArrayList.get(o).getIsVoid()) {
                    for (int n = 0; n < getGlobalConfingRes.getoMSOrderDeliveryItemId().size(); n++) {
                        if (getGlobalConfingRes.getoMSOrderDeliveryItemId().get(n).equalsIgnoreCase(Singletone.getInstance().itemsArrayList.get(o).getItemId())) {
                            isAlloMSOrderDeliveryItemOne = true;
                        }
                    }
                    if (!isAlloMSOrderDeliveryItemOne) {
                        isAlloMSOrderDeliveryItem = true;
                    }
                }
            }
            if (isAlloMSOrderDeliveryItem) {
                getMvpView().getCustomerModule().setCardPayment(false);
                doPayment(7);
            } else {
                showMessagePopup("No Items available");
            }

//            if (Singletone.getInstance().itemsArrayList.size() == 1) {
//                if (Singletone.getInstance().itemsArrayList.get(0).getItemId().equals("ESH0002")) {
//                    showMessagePopup("The Order contain only E shop shipping charge.");
//                } else {
//                    if (!Singletone.getInstance().itemsArrayList.get(0).getIsVoid()) {
//                        getMvpView().getCustomerModule().setCardPayment(false);
//                        doPayment(7);
//                    } else {
//                        showMessagePopup("No Items available");
//                    }
//                }
//            } else {
//                boolean isAllVoid = true;
//                for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
//                    if (!Singletone.getInstance().itemsArrayList.get(i).getItemId().equals("ESH0002")) {
//                        if (!Singletone.getInstance().itemsArrayList.get(i).getIsVoid()) {
//                            isAllVoid = false;
//                        }
//                    }
//                }
//                if (isAllVoid){
//                    showMessagePopup("No Items available");
//                }else{
//                    getMvpView().getCustomerModule().setCardPayment(false);
//                    doPayment(7);
//                }
//            }
        } else {
            showMessagePopup("No Items available");
        }

    }

    private void showMessagePopup(String message) {
        Dialog showMessagePopup = new Dialog(getMvpView().getContext());
        ExitInfoDialogBinding exitInfoDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getMvpView().getContext()), R.layout.exit_info_dialog, null, false);
        showMessagePopup.setCancelable(false);
        showMessagePopup.setContentView(exitInfoDialogBinding.getRoot());
        if (showMessagePopup.getWindow() != null)
            showMessagePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitInfoDialogBinding.title.setVisibility(View.VISIBLE);
        exitInfoDialogBinding.title.setText(message);

        exitInfoDialogBinding.subtitle.setVisibility(View.GONE);
        exitInfoDialogBinding.subtitle.setText(message);
        exitInfoDialogBinding.dialogButtonNO.setVisibility(View.GONE);
//        exitInfoDialogBinding.sepe
        exitInfoDialogBinding.dialogButtonOK.setText("OK");
        exitInfoDialogBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessagePopup.dismiss();
            }
        });
        showMessagePopup.show();

    }

    @Override
    public void onClickCodPayMode() {
        getMvpView().getCustomerModule().setCardPayment(false);
        doPayment(8);
    }

    @Override
    public void onClickCardPaymentPay() {

        //  System.out.println("iscardbilling-->" + getDataManager().getGlobalJson().ci);
        System.out.println("isezetap-->" + getDataManager().getGlobalJson().isISEzetapActive());
        if (TextUtils.isEmpty(getMvpView().getCardPaymentAmount())) {
            getMvpView().setErrorCardPaymentAmountEditText("Enter Amount");
        } else if (getMvpView().orderRemainingAmount() < Double.parseDouble(getMvpView().getCardPaymentAmount())) {
            getMvpView().setErrorCardPaymentAmountEditText("Entered Amount greater then Order amount");
        } else {
            // System.out.println("iscardbilling-->"+getDataManager().getGlobalJson().isISCardBilling());
            // System.out.println("isezetap-->"+getDataManager().getGlobalJson().isISEzetapActive());

            if (getDataManager().getGlobalJson().isISCardBilling() && getDataManager().getGlobalJson().isISEzetapActive()) {
                doInitializeEzeTap();
            } else if (getDataManager().getGlobalJson().isISCardBilling() && !getDataManager().getGlobalJson().isISEzetapActive() || getDataManager().getGlobalJson().isISHBPStore()) {
                generateTenterLineService(Double.parseDouble(getMvpView().getCardPaymentAmount()), null);
            } else {
                getMvpView().showMessage("Card Payment not available");
            }


            // generateTenterLineService(Double.parseDouble(getMvpView().getCardPaymentAmount()), null);
        }
    }


    /*@Override
    public void onClickSmsPAyMode() {

    }*/


    @Override
    public void onClickCashPaymentPay() {
        getMvpView().getCustomerModule().setCardPayment(false);
        if (!showErrorPharmaDocutor()) {
            if (TextUtils.isEmpty(getMvpView().getCashPaymentAmount())) {
                getMvpView().setErrorCashPaymentAmountEditText("Enter Amount");
            } else {

                if (!getMvpView().getCustomerModule().isExistingCustomerOrNot()) {
                    if (!getMvpView().isOnleneOrder() && getGlobalConfiguration().isISHBPStore()) {
                        if (getGlobalConfiguration().isISOneApolloCardCreationAllowed()) {
                            checkCustomerExistOrNot(getMvpView().getCustomerModule());
                        } else {
                            generateTenterLineService(Double.parseDouble(getMvpView().getCashPaymentAmount()), null);
                        }
                    } else {
                        generateTenterLineService(Double.parseDouble(getMvpView().getCashPaymentAmount()), null);
                    }
                } else {
                    generateTenterLineService(Double.parseDouble(getMvpView().getCashPaymentAmount()), null);
                }
            }
        } else {
            getMvpView().showDoctorSelectError();
        }

    }

    @Override
    public void onClickCreditPaymentPay() {
        getMvpView().getCustomerModule().setCardPayment(false);
        if (!showErrorPharmaDocutor()) {
            getPharmacyStaffApiDetails("", "ENQUIRY", getMvpView().orderRemainingAmount());
//            if (TextUtils.isEmpty(getMvpView().getCreditPaymentAmount())) {
//                getMvpView().setErrorCreditPaymentAmountEditText("Enter Amount");
//            } else {
//                generateTenterLineService(Double.parseDouble(getMvpView().getCreditPaymentAmount()));
//            }
        } else {
            getMvpView().showDoctorSelectError();
        }
    }

    @Override
    public void onClickOneApolloPaymentPay() {
        getMvpView().getCustomerModule().setCardPayment(false);
        if (getMvpView().orderRemainingAmount() <= Double.parseDouble(getMvpView().getOneApolloPoints())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                ValidatePointsReqModel oneApolloSendOtpReq = new ValidatePointsReqModel();
                ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
                requestDataEntity.setAction("SENDOTP");
                requestDataEntity.setCoupon("");
                requestDataEntity.setCustomerID("");
                requestDataEntity.setDocNum(getMvpView().getDoctorModule().getCode());
                requestDataEntity.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
                requestDataEntity.setOTP("");
                requestDataEntity.setPoints(String.valueOf(getMvpView().orderRemainingAmount()));
                requestDataEntity.setReqBy("M");
                requestDataEntity.setRRNO("");
                requestDataEntity.setStoreId(getDataManager().getStoreId());
                requestDataEntity.setType("");
                requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
                oneApolloSendOtpReq.setRequestData(requestDataEntity);
                Call<ValidatePointsResModel> call = api.ONE_APOLLO_SEND_OTP_RES_CALL(oneApolloSendOtpReq);
                call.enqueue(new Callback<ValidatePointsResModel>() {
                    @Override
                    public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            if (response.body() != null) {
                                getMvpView().hideLoading();
                                getMvpView().onSuccessOneApolloSendOtp(response.body().getOneApolloProcessResult());
                            } else {
                                getMvpView().hideLoading();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
            //getMvpView().setErrorCardPaymentAmountEditText("Entered Amount greater then Order amount");
        } else {
            getMvpView().onError("Apollo Points less than order amount");
        }
//        if (!TextUtils.isEmpty(getMvpView().getOneApolloPoints())) {
//            if (validateOneApolloPoints()) {
//                if (getMvpView().orderRemainingAmount() < Double.parseDouble(getMvpView().getOneApolloPoints())) {
//                    getMvpView().setErrorCardPaymentAmountEditText("Entered Amount greater then Order amount");
//                } else {
//                    if (getMvpView().isNetworkConnected()) {
//                        getMvpView().showLoading();
//                        //Creating an object of our api interface
//                        ApiInterface api = ApiClient.getApiService();
//                        ValidatePointsReqModel oneApolloSendOtpReq = new ValidatePointsReqModel();
//                        ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
//                        requestDataEntity.setAction("SENDOTP");
//                        requestDataEntity.setCoupon("");
//                        requestDataEntity.setCustomerID("");
//                        requestDataEntity.setDocNum(getMvpView().getDoctorModule().getCode());
//                        requestDataEntity.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
//                        requestDataEntity.setOTP("");
//                        requestDataEntity.setPoints(getMvpView().getOneApolloPoints());
//                        requestDataEntity.setReqBy("M");
//                        requestDataEntity.setRRNO("");
//                        requestDataEntity.setStoreId(getDataManager().getStoreId());
//                        requestDataEntity.setType("");
//                        requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
//                        oneApolloSendOtpReq.setRequestData(requestDataEntity);
//                        Call<ValidatePointsResModel> call = api.ONE_APOLLO_SEND_OTP_RES_CALL(oneApolloSendOtpReq);
//                        call.enqueue(new Callback<ValidatePointsResModel>() {
//                            @Override
//                            public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
//                                if (response.isSuccessful()) {
//                                    //Dismiss Dialog
//                                    if (response.body() != null) {
//                                        getMvpView().hideLoading();
//                                        getMvpView().onSuccessOneApolloSendOtp(response.body().getOneApolloProcessResult());
//                                    } else {
//                                        getMvpView().hideLoading();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
//                                //Dismiss Dialog
//                                getMvpView().hideLoading();
//                                handleApiError(t);
//                            }
//                        });
//                    } else {
//                        getMvpView().onError("Internet Connection Not Available");
//                    }
//                }
//            } else {
//                getMvpView().setErrorOneApolloPointsEditText("Enter valid Points");
//            }
//        } else {
//            getMvpView().setErrorOneApolloPointsEditText("Enter Points");
//        }
    }


    @Override
    public void Posttransactionrequest() {
        POSTransactionEntity posTransactionReq = posTransactionEntity();
        getMvpView().Posttratransactionrequest(posTransactionReq);

    }

    @Override
    public void onClickEditItemsList() {
        getMvpView().onClickEditItemsList();
    }

    @Override
    public void calculatePosTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.CALCULATE_POS_TRANSACTION_RES_CALL(posTransactionEntity());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().isManualDisc(false);
                        getMvpView().onSuccessCalculatePosTransaction(response.body());

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickOTPVerify() {
        if (!TextUtils.isEmpty(getMvpView().getOneApolloOtp())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                ValidatePointsReqModel oneApolloSendOtpReq = new ValidatePointsReqModel();
                ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
                requestDataEntity.setAction("VALOTP");
                requestDataEntity.setCoupon("");
                requestDataEntity.setCustomerID("");
                requestDataEntity.setDocNum(getMvpView().getDoctorModule().getCode());
                requestDataEntity.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
                requestDataEntity.setOTP(getMvpView().getOneApolloOtp());
                requestDataEntity.setPoints(getMvpView().getOneApolloPoints());
                requestDataEntity.setReqBy("M");
                requestDataEntity.setRRNO(getMvpView().getValidateOneApolloPoints().getRRNO());
                requestDataEntity.setStoreId(getDataManager().getStoreId());
                requestDataEntity.setType("");
                requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
                oneApolloSendOtpReq.setRequestData(requestDataEntity);
                Call<ValidatePointsResModel> call = api.ONE_APOLLO_SEND_OTP_RES_CALL(oneApolloSendOtpReq);
                call.enqueue(new Callback<ValidatePointsResModel>() {
                    @Override
                    public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            if (response.body() != null) {
                                getMvpView().hideLoading();
                                if (response.body().getOneApolloProcessResult().getStatus().equals("False"))
                                    getMvpView().showMessage(response.body().getOneApolloProcessResult().getMessage());
                                else
                                    getMvpView().onSuccessOneApolloOtp(response.body().getOneApolloProcessResult());
                                //generateTenterLineService();

                            } else {
                                getMvpView().hideLoading();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        } else {
            getMvpView().setErrorOneApolloOtpEditText("Enter Otp");
        }
    }

    @Override
    public void onClickReSendOTP() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().clearOTPVIew();
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ValidatePointsReqModel oneApolloSendOtpReq = new ValidatePointsReqModel();
            ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
            requestDataEntity.setAction("RESENDOTP");
            requestDataEntity.setCoupon("");
            requestDataEntity.setCustomerID("");
            requestDataEntity.setDocNum(getMvpView().getDoctorModule().getCode());
            requestDataEntity.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
            requestDataEntity.setOTP(getMvpView().getOneApolloOtp());
            requestDataEntity.setPoints(getMvpView().getOneApolloPoints());
            requestDataEntity.setReqBy("M");
            requestDataEntity.setRRNO(getMvpView().getValidateOneApolloPoints().getRRNO());
            requestDataEntity.setStoreId(getDataManager().getStoreId());
            requestDataEntity.setType("");
            requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
            oneApolloSendOtpReq.setRequestData(requestDataEntity);
            Call<ValidatePointsResModel> call = api.ONE_APOLLO_SEND_OTP_RES_CALL(oneApolloSendOtpReq);
            call.enqueue(new Callback<ValidatePointsResModel>() {
                @Override
                public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        if (response.body() != null) {
                            getMvpView().hideLoading();

                        } else {
                            getMvpView().hideLoading();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onSuccessCardPayment(double amount) {
        //  getMvpView().updatePayedAmount(Double.parseDouble(getMvpView().getCardPaymentAmount()),2);

        generateTenterLineService(amount, null);
    }

    @Override
    public void onClickGenerateBill() {
        saveRetailTransaction();
    }

    @Override
    public void onClickManualDisc() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<ManualDiscCheckRes> call = api.MANUAL_DISC_CHECK_RES_CALL(getManualDiscCheckReq());
            call.enqueue(new Callback<ManualDiscCheckRes>() {
                @Override
                public void onResponse(@NotNull Call<ManualDiscCheckRes> call, @NotNull Response<ManualDiscCheckRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().openManualDiscDialog(response.body());
                        } else if (response.body() != null) {
                            getMvpView().errorMessageDialog("Manual Discount", response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ManualDiscCheckRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickCouponDisc() {
        double categoryAmount = 0;
        for (GetTrackingWiseConfing._TrackingConfigrationEntity entity : getDataManager().getTrackingWiseConfing().get_TrackingConfigration()) {
            if (entity.getCorpCode().equalsIgnoreCase(getMvpView().getCorporateModule().getCode()) && entity.getISCouponBilling() == 1) {
                if (!TextUtils.isEmpty(entity.getCategory())) {
                    if (entity.getCategory().equalsIgnoreCase("PHARMA"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getPharmaTotalAmount();
                    else if (entity.getCategory().equalsIgnoreCase("FMCG"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getFmcgTotalAmount();
                    else if (entity.getCategory().equalsIgnoreCase("PL"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getPlTotalAmount();
                } else {
                    categoryAmount = 0;
                }
                break;
            }
        }
        getMvpView().showCouponCodeDialog(categoryAmount);
    }

    @Override
    public void toApplyManualDisc(ManualDiscCheckRes body, ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList, String fixedDiscountCode) {
        ManualDiscCheckReq manualDiscCheckReq = new ManualDiscCheckReq();
        manualDiscCheckReq.setAvailableDiscList(body.getAvailDiscountList());
        manualDiscCheckReq.setDiscList(body.getDiscList());
        manualDiscCheckReq.setDisplayList(displayListArrayList);
        manualDiscCheckReq.setFixedDiscountCode(fixedDiscountCode);
        manualDiscCheckReq.setAutoDiscount(false);
        manualDiscCheckReq.setClearAllDiscount(false);
        manualDiscCheckReq.setCreditAmount(0);
        manualDiscCheckReq.setEprescriptionDiscountPer(0);
        manualDiscCheckReq.setEprescriptionMaxDicountValue(0);
        manualDiscCheckReq.setHealingCardThresholdAmout(0);
        manualDiscCheckReq.setISDiscountCodeRequired(0);
        manualDiscCheckReq.setIsOTPRequired(0);
        manualDiscCheckReq.setNormalSale(true);
        manualDiscCheckReq.setOPTValidate(false);
        manualDiscCheckReq.setPosSalesTransaction(posTransactionEntity());
        manualDiscCheckReq.setRequestStatus(0);
        manualDiscCheckReq.setRequestType("APPLYMANNUALDISCOUNT");

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<ManualDiscCheckRes> call = api.MANUAL_DISC_CHECK_RES_CALL(manualDiscCheckReq);
            call.enqueue(new Callback<ManualDiscCheckRes>() {
                @Override
                public void onResponse(@NotNull Call<ManualDiscCheckRes> call, @NotNull Response<ManualDiscCheckRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().isManualDisc(true);
                            getMvpView().onSuccessCalculatePosTransaction(response.body().getPosSalesTransaction());
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ManualDiscCheckRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void generateOTP() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            String url = getDataManager().getGlobalJson().getSMSAPI();
            String otp = String.valueOf(CommonUtils.generatorOTP(8));
            String message = "Dear Customer, Your Smart Saver Offer OTP is " + otp + ",Use the OTP to avail the Offer.APLPHR";
            Call<OTPRes> call = api.GENERATE_OTP_RES_CALL(url.replace("{0}", getMvpView().getCustomerModule().getMobileNo()).replace("{1}", message));
            call.enqueue(new Callback<OTPRes>() {
                @Override
                public void onResponse(@NotNull Call<OTPRes> call, @NotNull Response<OTPRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("OK")) {
                            getMvpView().generateOTPResponseSuccess(otp);
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OTPRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void validateOneApolloPoints(String userMobileNumber, String transactionID) {
        if (getMvpView().isNetworkConnected()) {
            //  getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ValidatePointsReqModel pointsReqModel = new ValidatePointsReqModel();
            ValidatePointsReqModel.RequestDataEntity requestDataEntity = new ValidatePointsReqModel.RequestDataEntity();
            requestDataEntity.setStoreId("");
            requestDataEntity.setDocNum(transactionID);
            requestDataEntity.setMobileNum(userMobileNumber);
            requestDataEntity.setReqBy("M");
            requestDataEntity.setRRNO("");
            requestDataEntity.setOTP("");
            requestDataEntity.setAction("BALANCECHECK");
            requestDataEntity.setCoupon("");
            requestDataEntity.setType("");
            requestDataEntity.setCustomerID("");
            requestDataEntity.setUrl(getDataManager().getGlobalJson().getOneApolloURL());
            pointsReqModel.setRequestData(requestDataEntity);
            Call<ValidatePointsResModel> call = api.VALIDATE_ONE_APOLLO_POINTS(pointsReqModel);
            call.enqueue(new Callback<ValidatePointsResModel>() {
                @Override
                public void onResponse(@NotNull Call<ValidatePointsResModel> call, @NotNull Response<ValidatePointsResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessValidateOneApolloPoints(response.body());
                        } else {
                            getMvpView().onFailedValidateOneApolloPoints(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void checkProductTrackingWise() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.CHECK_PRODUCT_TRACKING_WISE_RES_CALL(posTransactionEntity());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().onSuccessCheckProductTrackingWise(response.body());

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void changeQuantity(SalesLineEntity salesLineEntity, double quantity) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.CHANGE_QUANTITY_RES_CALL(salesLineEntity.getLineNo(), quantity, posTransactionEntity());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            salesLineEntity.setQty(quantity);
                            calculatePosTransaction();
                        } else {
                            getMvpView().partialPaymentDialog("", response.body().getReturnMessage());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    //Circle plan Api calls......
    @Override
    public void CircleplanCashbackapicall() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            //  Log.d("entity body-->", String.valueOf(api));
            Call<CircleMemebershipCashbackPlanResponse> call = api.circleMembershipCashback(posTransactionEntity());
            call.enqueue(new Callback<CircleMemebershipCashbackPlanResponse>() {
                @Override
                public void onResponse(@NotNull Call<CircleMemebershipCashbackPlanResponse> call, @NotNull Response<CircleMemebershipCashbackPlanResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().CirclecashbackplanSuccess(response.body());
                        } else {
                            getMvpView().CirclecashbackplanFailure(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CircleMemebershipCashbackPlanResponse> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");

        }


    }


    private CalculatePosTransactionRes tenderLineEntities = new CalculatePosTransactionRes();

    @Override
    public void generateTenterLineService(double amount, WalletServiceRes walletServiceRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();

            // if (Constant.getInstance().isomsorder) {
            // Creating an object of DecimalFormat class
            DecimalFormat df_obj = new DecimalFormat("#.##");
            String number = df_obj.format(amount);
            double roundedvalue = Double.parseDouble(number);
            amount = roundedvalue;
            //double tempbalanceAmt = orderTotalAmount() - roundedvalue;
               /* if (tempbalanceAmt == 0) {
                    paymentDoneAmount = roundedv
                */

            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<GenerateTenderLineRes> call = api.GENERATE_TENDER_LINE_RES_CALL(amount, generateTenderLineReq(amount, walletServiceRes));
            call.enqueue(new Callback<GenerateTenderLineRes>() {
                @Override
                public void onResponse(@NotNull Call<GenerateTenderLineRes> call, @NotNull Response<GenerateTenderLineRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getGenerateTenderLineResult().getRequestStatus() == 0) {
                            tenderLineEntities = response.body().getGenerateTenderLineResult();
                            // tenderLineEntities.addAll(response.body().getGenerateTenderLineResult().getTenderLine());
                            getMvpView().updatePayedAmount(response.body().getGenerateTenderLineResult());
                            //  saveRetailTransaction(response.body());
                        } else {
                            getMvpView().onFailedGenerateTenderLine(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GenerateTenderLineRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


    //get tender line service for vendor type........
   /* private GenerateTenderLineReq generateTenderLineReqforOMSOrder(CustomerDataResBean customerDataResBean) {
        GenerateTenderLineReq tenderLineReq = new GenerateTenderLineReq();
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTender("credit");
        typeEntity.setTenderCombinationType(3);
        typeEntity.setTenderLimit(60000);
        typeEntity.setTenderTypeId("10");
        typeEntity.setPosOpereration(0);
        typeEntity.setRoundingMethod(0);
        typeEntity.setTenderURL(null);
        tenderLineReq.setType(typeEntity);
        tenderLineReq.setPOSTransaction(getMvpView().getCalculatedPosTransactionRes());
        tenderLineReq.setWallet(null);
        return tenderLineReq;
    }*/

    @Override
    public void validateOmsOrder(double totalamount, CalculatePosTransactionRes calculatePosTransactionRes, CustomerDataResBean customerDataResBean) {
        if (calculatePosTransactionRes.getISOMSOrder()) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();


           /* CalculatePosTransactionRes OMScalculatepostransactionres=new CalculatePosTransactionRes();
            OMScalculatepostransactionres.setRemainingamount(0);
            OMScalculatepostransactionres.setTier("");
            OMScalculatepostransactionres.setCustomerType("");
            OMScalculatepostransactionres.setStockStatus("");
            OMScalculatepostransactionres.setIsUHIDBilling(false);
            OMScalculatepostransactionres.setHCOfferCode("");
            OMScalculatepostransactionres.setDiscountStatus(0);
            OMScalculatepostransactionres.setDiscountReferenceID("");
            OMScalculatepostransactionres.setISOnlineOrder(false);
            OMScalculatepostransactionres.setISCancelled(false);
            OMScalculatepostransactionres.setVendorCode("");
            OMScalculatepostransactionres.setISReserved(false);
            OMScalculatepostransactionres.setISBulkBilling(false);
            OMScalculatepostransactionres.setDeliveryDate(customerDataResBean.getDeliveryDate());
            OMScalculatepostransactionres.setOrderType(customerDataResBean.getOrderType());
            OMScalculatepostransactionres.setOrderSource(customerDataResBean.getOrderSource());
            OMScalculatepostransactionres.setShippingMethod(customerDataResBean.getShippingMethod());
            OMScalculatepostransactionres.setShippingMethodDesc(customerDataResBean.getShippingMethodDesc());
            OMScalculatepostransactionres.setBillingCity(customerDataResBean.getBillingCity());
            OMScalculatepostransactionres.setVendorId(customerDataResBean.getVendorId());
            OMScalculatepostransactionres.setPaymentSource(customerDataResBean.getPaymentSource());
            OMScalculatepostransactionres.setISPrescibeDiscount(false);
            OMScalculatepostransactionres.setCancelReasonCode("");
            OMScalculatepostransactionres.setStoreName("");
            OMScalculatepostransactionres.setRegionCode("");
            OMScalculatepostransactionres.setCustomerID("");
            OMScalculatepostransactionres.setCorpCode(calculatePosTransactionRes.getCorpCode());
            OMScalculatepostransactionres.setMobileNO(calculatePosTransactionRes.getMobileNO());
            OMScalculatepostransactionres.setDOB(customerDataResBean.getDOB());
            OMScalculatepostransactionres.setCustomerName(calculatePosTransactionRes.getCustomerName());
            OMScalculatepostransactionres.setCustAddress(customerDataResBean.getCustAddress());
            OMScalculatepostransactionres.setCustomerState(customerDataResBean.getState());
            OMScalculatepostransactionres.setGender(calculatePosTransactionRes.getGender());
            OMScalculatepostransactionres.setPincode(customerDataResBean.getPincode());
            OMScalculatepostransactionres.setDoctorName(calculatePosTransactionRes.getDoctorName());
            OMScalculatepostransactionres.setDoctorCode(calculatePosTransactionRes.getDoctorCode());
            OMScalculatepostransactionres.setSalesOrigin("8");
            OMScalculatepostransactionres.setTrackingRef(calculatePosTransactionRes.getTrackingRef());
            OMScalculatepostransactionres.setREFNO(calculatePosTransactionRes.getTrackingRef());
            OMScalculatepostransactionres.setIPNO("");
            OMScalculatepostransactionres.setIPSerialNO("");
            OMScalculatepostransactionres.setReciptId("");
            OMScalculatepostransactionres.setBatchTerminalid("");
            OMScalculatepostransactionres.setBusinessDate(calculatePosTransactionRes.getBusinessDate());
            OMScalculatepostransactionres.setChannel(calculatePosTransactionRes.getChannel());
            OMScalculatepostransactionres.setComment("");
            OMScalculatepostransactionres.setCreatedonPosTerminal(calculatePosTransactionRes.getCreatedonPosTerminal());
            OMScalculatepostransactionres.setCurrency("INR");
            OMScalculatepostransactionres.setCustAccount("");
            OMScalculatepostransactionres.setCustDiscamount(0);
            OMScalculatepostransactionres.setDiscAmount(0);
            OMScalculatepostransactionres.setEntryStatus(0);
            OMScalculatepostransactionres.setGrossAmount(calculatePosTransactionRes.getGrossAmount());
            OMScalculatepostransactionres.setNetAmount(calculatePosTransactionRes.getNetAmount());
            OMScalculatepostransactionres.setNetAmountInclTax(calculatePosTransactionRes.getNetAmountInclTax());*/
/*

            getMvpView().getCalculatedPosTransactionRes().setBillingCity(calculatePosTransactionRes.getBillingCity());
            getMvpView().getCalculatedPosTransactionRes().setCurrentSalesLine(0);
            getMvpView().getCalculatedPosTransactionRes().setCustAddress(customerDataResBean.getCustAddress());
            getMvpView().getCalculatedPosTransactionRes().setCustomerState(customerDataResBean.getCustomerState());
            getMvpView().getCalculatedPosTransactionRes().setDeliveryDate(customerDataResBean.getDeliveryDate());
            getMvpView().getCalculatedPosTransactionRes().setDOB(customerDataResBean.getDOB());
            getMvpView().getCalculatedPosTransactionRes().setExpiryDays(90);
            getMvpView().getCalculatedPosTransactionRes().setGender(customerDataResBean.getGender());
            getMvpView().getCalculatedPosTransactionRes().setISAdvancePayment(false);
            getMvpView().getCalculatedPosTransactionRes().setISOMSValidate(true);
            getMvpView().getCalculatedPosTransactionRes().setOrderPrescriptionURL(customerDataResBean.getOrderPrescriptionURL());
            getMvpView().getCalculatedPosTransactionRes().setOrderSource(customerDataResBean.getOrderSource());
            getMvpView().getCalculatedPosTransactionRes().setOrderType(customerDataResBean.getOrderType());
            getMvpView().getCalculatedPosTransactionRes().setPaymentSource(customerDataResBean.getPaymentSource());
            getMvpView().getCalculatedPosTransactionRes().setPincode(customerDataResBean.getPincode());
            getMvpView().getCalculatedPosTransactionRes().setREFNO(calculatePosTransactionRes.getTrackingRef());
            getMvpView().getCalculatedPosTransactionRes().setSalesOrigin("8");
            getMvpView().getCalculatedPosTransactionRes().setShippingMethod(customerDataResBean.getShippingMethod());
            getMvpView().getCalculatedPosTransactionRes().setShippingMethodDesc(customerDataResBean.getShippingMethod());
*/

                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                Call<GenerateTenderLineRes> call = api.VALIDATE_OMS_ORDER(generateTenderLineReq(totalamount, null));
                call.enqueue(new Callback<GenerateTenderLineRes>() {
                    @Override
                    public void onResponse(@NotNull Call<GenerateTenderLineRes> call, @NotNull Response<GenerateTenderLineRes> response) {
                        if (response.isSuccessful()) {
                            getMvpView().hideLoading();
                            if (response.body() != null && response.body().getValidateOMSOrderResult().getRequestStatus() == 0) {
//                            if (response.body() != null) {

                                tenderLineEntities = response.body().getValidateOMSOrderResult();
                                if (tenderLineEntities.getTenderLine().size() > 0) {
                                    getMvpView().updatePayedAmount(response.body().getValidateOMSOrderResult());

                                    if (Constant.getInstance().remainamount > 0) {
                                        getMvpView().omsremainamount(Constant.getInstance().remainamount);
                                        Constant.getInstance().vendorcredit = true;
                                    }
                                    //getMvpView().updatePayedAmount(response.body().getGenerateTenderLineResult());

                                } else {
                                    getMvpView().onSucessOMSOrderValidate(response.body().getGenerateTenderLineResult());
                                }

                            } else {
                                getMvpView().onFailedGenerateTenderLine(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<GenerateTenderLineRes> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        } else {
            Dialog dialog = new Dialog(getMvpView().getContext());//, R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getMvpView().getContext()), R.layout.dialog_cancel, null, false);
            dialogCancelBinding.dialogMessage.setText("Reference not available please clear transaction and do again!....");
            dialog.setContentView(dialogCancelBinding.getRoot());
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
            dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
//                if (getGlobalConfiguration().getMPOSVersion() != null && getGlobalConfiguration().getMPOSVersion().equals("2")) {
//                    Intent intent = new Intent(getMvpView().getContext(), PickerNavigationActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("FRAGMENT_NAME", "BILLER");
//                    intent.putExtra("EXIT", true);
//                    getMvpView().getContext().startActivity(intent);
////                getMvpView().getContext().overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
//                } else {
                onClickBackPressed();
//                }
                dialog.dismiss();
            });
//            dialogCancelBinding.dialogButtonNot.setOnClickListener(v -> dialog.dismiss());
        }
    }


    @Override
    public void clearAllVoidTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            if (getMvpView().getCalculatedPosTransactionRes().getTenderLine().size() > 0) {
                getMvpView().getCalculatedPosTransactionRes().getTenderLine().clear();
            }
            if (tenderLineEntities.getTenderLine().size() > 0) {
                tenderLineEntities.getTenderLine().clear();
            }

            CalculatePosTransactionRes calculatePosTransactionRes = getMvpView().getCalculatedPosTransactionRes();
            if (getMvpView().isCameFromOrderDetailsScreenActivity()) {
                calculatePosTransactionRes.setIsMPOSBill(2);
            }

            Call<CalculatePosTransactionRes> call = api.VOID_TRANSACTION(calculatePosTransactionRes);//getMvpView().getCalculatedPosTransactionRes()
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().isManualDisc(false);
                            getMvpView().onSuccessClearAll();
                        } else {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }

                    } else {
                        getMvpView().showMessage(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void closeOrderVoidTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            CalculatePosTransactionRes calculatePosTransactionRes = getMvpView().getCalculatedPosTransactionRes();
            if (getMvpView().isCameFromOrderDetailsScreenActivity()) {
                calculatePosTransactionRes.setIsMPOSBill(2);
            }

            Call<CalculatePosTransactionRes> call = api.VOID_TRANSACTION(calculatePosTransactionRes);//getMvpView().getCalculatedPosTransactionRes()
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().isManualDisc(false);
                            getMvpView().closeOrderSuccess();
                        } else {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }

                    } else {
                        getMvpView().showMessage(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void voidProduct(int lineNumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.VOID_PRODUCT(lineNumber, getMvpView().getCalculatedPosTransactionRes());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().isManualDisc(false);
                            getMvpView().onSuccessCalculatePosTransaction(response.body());
                        } else {
                            getMvpView().partialPaymentDialog("", response.body().getReturnMessage());
                        }

                    } else {
                        getMvpView().showMessage(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void checkAllowedPaymentMode(PaymentMethodModel paymentMethodModel) {
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(false);
        paymentMethodModel.setSmsPayMode(false);
        paymentMethodModel.setOneApolloMode(false);
        paymentMethodModel.setWalletMode(false);
        paymentMethodModel.setPhonePeQrCodeMode(false);
        paymentMethodModel.setCreditMode(false);
        paymentMethodModel.setVendorPayMode(false);
        if (getDataManager().getAllowedPaymentModeRes() != null && getDataManager().getAllowedPaymentModeRes().get_PaymentMethodList().size() > 0) {
            for (AllowedPaymentModeRes._PaymentMethodListEntity entity : getDataManager().getAllowedPaymentModeRes().get_PaymentMethodList()) {
                // System.out.println("Paymentmethod combination code--->" + entity.getCombinationCode());
                // System.out.println("Payment mode--->1-->" + entity.getPaymentMode());
                //System.out.println("Payment corprate mode--->2-->" + getMvpView().getCorporateModule().getPayMode());
                boolean isHbpStore = true;
                if (getMvpView().isOnleneOrder() && isHbpStore) {//getGlobalConfiguration().isISHBPStore()
                    paymentMethodModel.setEnableCashBtn(true);
                    paymentMethodModel.setEnableCardBtn(true);
                    paymentMethodModel.setEnableWalletBtn(true);
                    paymentMethodModel.setEnableVendorPayBtn(false);
                    paymentMethodModel.setEnableCreaditBtn(false);
                    paymentMethodModel.setEnableApolloBtn(false);
                    paymentMethodModel.setSmsPayMode(false);

                    if (!getDataManager().getGlobalJson().isISBillingPaymentAllowed()) {
                        paymentMethodModel.setEnableCashBtn(false);
                        paymentMethodModel.setEnableCardBtn(false);
                        paymentMethodModel.setSmsPayMode(false);
                        paymentMethodModel.setEnableApolloBtn(false);
                        paymentMethodModel.setEnableWalletBtn(false);
                        paymentMethodModel.setEnableCreaditBtn(false);
                        paymentMethodModel.setEnableVendorPayBtn(false);
                    }
                } else {
                    if (getMvpView().getCorporateModule().getPayMode() != null)
                        if (getMvpView().getCorporateModule().getPayMode().equals(entity.getPaymentMode())) {
                            // System.out.println("Paymentmode--->3-->" + getMvpView().getCorporateModule().getPayMode());
                            //Cash Tender
                            if (!entity.getCombinationCode().contains("1")) {
                                paymentMethodModel.setEnableCashBtn(false);
                            } else {
                                paymentMethodModel.setEnableCashBtn(true);
                            }


                            //Credit Tender
                            if (!entity.getCombinationCode().contains("3")) {
                                paymentMethodModel.setEnableCreaditBtn(false);
                            } else {
                                if (getMvpView().getCorporateModule().getCode().equals(getDataManager().getGlobalJson().getHealingCardCorpId())) {
                                    paymentMethodModel.setEnableCreaditBtn(false);
                                } else if (!getMvpView().getCorporateModule().getCode().equals(getDataManager().getGlobalJson().getHealingCardCorpId())) {
                                    paymentMethodModel.setEnableCreaditBtn(true);
                                } else {
                                    paymentMethodModel.setEnableCreaditBtn(false);
                                }
                            }

                            //Card Tender
                            if (!entity.getCombinationCode().contains("2")) {
                                paymentMethodModel.setEnableCardBtn(false);
                            } else {
                                if (!getGlobalConfiguration().isISHBPStore()) {
                                    if (getDataManager().getGlobalJson().isISCardBilling() && !getDataManager().getGlobalJson().isISEzetapActive()) {
                                        paymentMethodModel.setEnableCardBtn(false);
                                    } else if (!getDataManager().getGlobalJson().isISCardBilling() || !getDataManager().getGlobalJson().isISEzetapActive()) {
                                        paymentMethodModel.setEnableCardBtn(false);
                                    } else {
                                        paymentMethodModel.setEnableCardBtn(true);
                                    }
                                } else {
                                    paymentMethodModel.setEnableCardBtn(true);
                                }

                            }


                            //  paymentMethodModel.setEnableSmsPayBtn(true);
                            //Gift Tender
                            if (!entity.getCombinationCode().contains("6")) {
                                paymentMethodModel.setEnableApolloBtn(false);
                            } else {
                                paymentMethodModel.setEnableApolloBtn(true);
                            }

                            //Vendor Payment Tender
                            if (!entity.getCombinationCode().contains("7")) {
                                paymentMethodModel.setEnableVendorPayBtn(false);
                            } else {
                                paymentMethodModel.setEnableVendorPayBtn(true);
                            }

                            //Wallets Tender
                            if (!entity.getCombinationCode().contains("5")) {
                                paymentMethodModel.setEnableWalletBtn(false);
                            } else {
                                paymentMethodModel.setEnableWalletBtn(true);
                            }


                            //Smspay tender.......
                            if (!entity.getCombinationCode().contains("5")) {
                                //  System.out.println("Smspay false--->" + entity.getCombinationCode());
                                paymentMethodModel.setEnableSmsPayBtn(false);
                            } else {
                                //  System.out.println("smspay true--->" + entity.getCombinationCode());
                                paymentMethodModel.setEnableSmsPayBtn(true);
                            }

                            //paymentMethodModel.setEnableSmsPayBtn(true);
//            if (drpTrackingRef.EditValue.ToString() == "172")
//            {
//                btnIPPayment.Enabled = true;
//            }
//            else
//            {
//                btnIPPayment.Enabled = false;
//            }
            /*if (POSSalesTransaction.IsReturn == true)
                            {
                                btnCash.Enabled = true;
                                btnCredits.Enabled = true;
                                btnHealingCard.Enabled = false;
                                btnCard.Enabled = false;
                                btnGift.Enabled = false;
                                btnWallets.Enabled = false;
                            }*/

                            if (!getDataManager().getGlobalJson().isISBillingPaymentAllowed()) {
                                paymentMethodModel.setEnableCashBtn(false);
                                paymentMethodModel.setEnableCardBtn(false);
                                paymentMethodModel.setSmsPayMode(false);
                                paymentMethodModel.setEnableApolloBtn(false);
                                paymentMethodModel.setEnableWalletBtn(false);
                                paymentMethodModel.setEnableCreaditBtn(false);
                                paymentMethodModel.setEnableVendorPayBtn(false);
                            }
                        }
                }
            }
        }
    }

    @Override
    public void onSelectPhonePeQrMode() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        if (getMvpView().isOnleneOrder()) {
            walletServiceReq.setPOSTransactionID(getMvpView().getOnlineTransactionId());
        } else {
            walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        }
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        String tenderurl = "";

        if (getDataManager().getTenderTypeResultEntity().get_TenderType().size() > 0) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase("Pay through QR Code") || tenderTypeEntity.getTender().equalsIgnoreCase("QR Code")) {
                    walletServiceReq.setWalletType(Integer.parseInt(tenderTypeEntity.getTenderTypeId()));
                    walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
                    tenderurl = tenderTypeEntity.getTenderURL();
                }
            }
            getMvpView().getPaymentMethod().setPhonePeMode(false);
            getMvpView().getPaymentMethod().setPhonePeQrMode(true);
            getMvpView().getPaymentMethod().setPaytmMode(false);
            getMvpView().getPaymentMethod().setAirtelMode(false);
            getMvpView().getPaymentMethod().setCashMode(false);
            getMvpView().getPaymentMethod().setCardMode(false);
            getMvpView().getPaymentMethod().setOneApolloMode(false);
            getMvpView().getPaymentMethod().setWalletMode(false);
            getMvpView().getPaymentMethod().setPhonePeQrCodeMode(true);
            getMvpView().getPaymentMethod().setCreditMode(false);
            showWalletPhonepeQrcodePaymentDialog("Pay through QR Code", true, walletServiceReq, tenderurl);
        }
    }

    @Override
    public void onSelectPhonePe() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        if (getMvpView().isOnleneOrder()) {
            walletServiceReq.setPOSTransactionID(getMvpView().getOnlineTransactionId());
        } else {
            walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        }
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        if (getDataManager().getTenderTypeResultEntity().get_TenderType().size() > 0) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase("PhonePe")) {
                    walletServiceReq.setWalletType(4);
                    walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
                }
            }
            getMvpView().getPaymentMethod().setPhonePeMode(true);
            getMvpView().getPaymentMethod().setPhonePeQrMode(false);
            getMvpView().getPaymentMethod().setPaytmMode(false);
            getMvpView().getPaymentMethod().setAirtelMode(false);
            getMvpView().getPaymentMethod().setCashMode(false);
            getMvpView().getPaymentMethod().setCardMode(false);
            getMvpView().getPaymentMethod().setOneApolloMode(false);
            getMvpView().getPaymentMethod().setWalletMode(true);
            getMvpView().getPaymentMethod().setPhonePeQrCodeMode(false);
            getMvpView().getPaymentMethod().setCreditMode(false);
            showWalletPaymentDialog("PhonePe Transaction", true, walletServiceReq);
        }
    }

    @Override
    public void onSelectPayTm() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        if (getMvpView().isOnleneOrder()) {
            walletServiceReq.setPOSTransactionID(getMvpView().getOnlineTransactionId());
        } else {
            walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        }
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        if (getDataManager().getTenderTypeResultEntity() != null) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase("PAYTM")) {
                    walletServiceReq.setWalletType(3);
                    walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
                }
            }
            getMvpView().getPaymentMethod().setPhonePeMode(false);
            getMvpView().getPaymentMethod().setPhonePeQrMode(false);
            getMvpView().getPaymentMethod().setPaytmMode(true);
            getMvpView().getPaymentMethod().setAirtelMode(false);
            getMvpView().getPaymentMethod().setCashMode(false);
            getMvpView().getPaymentMethod().setCardMode(false);
            getMvpView().getPaymentMethod().setOneApolloMode(false);
            getMvpView().getPaymentMethod().setWalletMode(true);
            getMvpView().getPaymentMethod().setPhonePeQrCodeMode(false);
            getMvpView().getPaymentMethod().setCreditMode(false);
            showWalletPaymentDialog("Paytm Transaction", false, walletServiceReq);
        } else {
            getMvpView().showMessage("TenderType missing");
        }
    }

    @Override
    public void onSelectAirtelMoney() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        if (getMvpView().isOnleneOrder()) {
            walletServiceReq.setPOSTransactionID(getMvpView().getOnlineTransactionId());
        } else {
            walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        }
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
            if (tenderTypeEntity.getTender().equalsIgnoreCase("Airtel")) {
                walletServiceReq.setWalletType(2);
                walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
            }
        }
        getMvpView().getPaymentMethod().setPhonePeMode(false);
        getMvpView().getPaymentMethod().setPhonePeQrMode(false);
        getMvpView().getPaymentMethod().setPaytmMode(false);
        getMvpView().getPaymentMethod().setAirtelMode(true);
        getMvpView().getPaymentMethod().setCashMode(false);
        getMvpView().getPaymentMethod().setCardMode(false);
        getMvpView().getPaymentMethod().setOneApolloMode(false);
        getMvpView().getPaymentMethod().setWalletMode(true);
        getMvpView().getPaymentMethod().setPhonePeQrCodeMode(false);
        getMvpView().getPaymentMethod().setCreditMode(false);
        showWalletPaymentDialog("Airtel Money Transaction", true, walletServiceReq);
    }

    @Override
    public boolean validTenderLimit(double amount, String tenderName) {
        if (getDataManager().getTenderTypeResultEntity() != null && getDataManager().getTenderTypeResultEntity().get_TenderType() != null) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase(tenderName)) {
                    if (tenderTypeEntity.getTenderLimit() < amount) {
                        getMvpView().partialPaymentDialog("", "Allowed Tender Limit is " + tenderTypeEntity.getTenderLimit() + "!");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void getPharmacyStaffApiDetails(String otp, String action, double amount) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            PharmacyStaffAPIReq staffAPIReq = new PharmacyStaffAPIReq();
            staffAPIReq.setAction(action);
            staffAPIReq.setAmount(amount);
            staffAPIReq.setDocNum(getMvpView().getDoctorModule().getCode());

            if (TextUtils.isEmpty(getMvpView().getCorporateModule().getPrg_Tracking())) {
                getMvpView().corpPrgTrackingError();
                getMvpView().hideLoading();
                return;
            } else staffAPIReq.setEmpId(getMvpView().getCorporateModule().getPrg_Tracking());

            staffAPIReq.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
            staffAPIReq.setOTP(otp);
            staffAPIReq.setRegion(getDataManager().getGlobalJson().getRegion());
            staffAPIReq.setSiteId(getDataManager().getGlobalJson().getStoreID());
            staffAPIReq.setSiteName(getDataManager().getGlobalJson().getStoreName());
            staffAPIReq.setCorpCode(getMvpView().getCorporateModule().getCode());
            staffAPIReq.setUrl(getDataManager().getGlobalJson().getDSBillingURL());
            Call<PharmacyStaffApiRes> call = api.PHARMACY_STAFF_API_RES_CALL(staffAPIReq);
            Gson gson = new Gson();
            //   System.out.println("requestforpharmacy_staff-->" + gson.toJson(staffAPIReq));
            call.enqueue(new Callback<PharmacyStaffApiRes>() {
                @Override
                public void onResponse(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Response<PharmacyStaffApiRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if (action.equalsIgnoreCase("ENQUIRY")) {
                                double availableAmount = Double.parseDouble(response.body().getTotalBalance()) - Double.parseDouble(response.body().getUsedBalance());
                                if (amount <= availableAmount) {
                                    getPharmacyStaffApiDetails(otp, "GENOTP", amount);
                                } else {
                                    getMvpView().partialPaymentDialog("", "Balance Not Available");
                                }
                            } else if (action.equalsIgnoreCase("GENOTP")) {
                                if (response.body().getValidateOTP().equalsIgnoreCase("true")) {
                                    getMvpView().showOTPPopUp(amount, response.body().getOTP());
                                } else {
                                    getMvpView().getPaymentMethod().setCreditMode(true);
                                    generateTenterLineService(amount, null);
                                }
                            } else if (action.equalsIgnoreCase("VALOTP")) {
                                if (response.body().getStatus().equalsIgnoreCase("true")) {
                                    getMvpView().getPaymentMethod().setCreditMode(true);
                                    generateTenterLineService(amount, null);
                                } else {
                                    getMvpView().getPaymentMethod().setCreditMode(true);
                                    generateTenterLineService(amount, null);
                                }
                            }
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void applyCouponCodeApi(String couponCode, double categoryAmount) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CouponDiscount> call = api.COUPON_DISCOUNT_CALL(getCouponDiscountRequest(couponCode, categoryAmount));
            call.enqueue(new Callback<CouponDiscount>() {
                @Override
                public void onResponse(@NotNull Call<CouponDiscount> call, @NotNull Response<CouponDiscount> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if (response.body().getCouponEnquiryDetailsResult() != null && response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult() != null && response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult().getRequestStatus()) {
                                getMvpView().getCalculatedPosTransactionRes().setCouponCode(couponCode);
                                getMvpView().getPaymentMethod().setCreditMode(true);
                                generateTenterLineService(Double.parseDouble(response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult().getCreditAmount()), null);
                            } else {
                                getMvpView().partialPaymentDialog("", response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult().getRequestMessage());
                            }
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CouponDiscount> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public String getStoreName() {
        return getDataManager().getGlobalJson().getStoreName();
    }

    @Override
    public String getStoreId() {
        return getDataManager().getStoreId();
    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public void onUploadApiCall() {
        getMvpView().onUploadApiCall();
    }

    @Override
    public void getUplaodPharmacyStaffApiDetails(String action) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            PharmacyStaffAPIReq staffAPIReq = new PharmacyStaffAPIReq();
            staffAPIReq.setAction(action);
            staffAPIReq.setAmount(0.0);
            staffAPIReq.setDocNum(getMvpView().getDoctorModule().getCode());

            if (TextUtils.isEmpty(getMvpView().getCorporateModule().getPrg_Tracking())) {
                getMvpView().corpPrgTrackingError();
                getMvpView().hideLoading();
                return;
            } else staffAPIReq.setEmpId(getMvpView().getCorporateModule().getPrg_Tracking());

            staffAPIReq.setMobileNum(getMvpView().getCustomerModule().getMobileNo());
            staffAPIReq.setOTP("");
            staffAPIReq.setRegion(getDataManager().getGlobalJson().getRegion());
            staffAPIReq.setSiteId(getDataManager().getGlobalJson().getStoreID());
            staffAPIReq.setSiteName(getDataManager().getGlobalJson().getStoreName());
            staffAPIReq.setUrl(getDataManager().getGlobalJson().getDSBillingURL());
            Call<PharmacyStaffApiRes> call = api.PHARMACY_STAFF_API_RES_CALL(staffAPIReq);
            call.enqueue(new Callback<PharmacyStaffApiRes>() {
                @Override
                public void onResponse(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Response<PharmacyStaffApiRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0 && response.body().getMessage().equalsIgnoreCase("Data Founds")) {
                            if (action.equalsIgnoreCase("ENQUIRY")) {
                                getMvpView().onSucessStaffListData(response.body());

                            }
                        } else {
                            getMvpView().onFaliureStaffListData();
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    //this code wrote by gopal on 11-01-2021.......
    SmsPaymentDialog smsPaymentDialog;

    public void showsmsPaymentDialog() {
        smsPaymentDialog = new SmsPaymentDialog(getMvpView().getContext());

        smsPaymentDialog.setCalculatedPosTransaction(getMvpView().getCalculatedPosTransactionRes());
        smsPaymentDialog.setPaymentMethod(getMvpView().getPaymentMethod());

        smsPaymentDialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPaymentDialog.dismiss();
            }
        });

        smsPaymentDialog.setGenerateLinkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsPaymentDialog.isValidateAmount()) {
                    GetSMSPayAPIRequest request = new GetSMSPayAPIRequest();
                    request.setOperationType("ENQUERY");
                    request.setTransactionId(smsPaymentDialog.getTransactionId());
                    request.setCorpCode(getMvpView().getCalculatedPosTransactionRes().getCorpCode());
                    request.setRequestType("PAYTM");
                    request.setAmount(smsPaymentDialog.getWalletAmount());
                    request.setOrderNumnber(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                    request.setCustomerContactNumber(smsPaymentDialog.getWalletMobileNumber());
                    request.setCustomerName(getMvpView().getCalculatedPosTransactionRes().getCustomerName());
                    request.setCustomerEmail("");
                    request.setIsHBP(getDataManager().getGlobalJson().isISHBPStore());
                    request.setStoreId(getMvpView().getCalculatedPosTransactionRes().getStore());
                    request.setPaytmId("");
                    request.setStatus(false);
                    request.setMessage("");
                    request.setOrderId("");
                    request.setURL(getDataManager().getGlobalJson().SMSPayURL());
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<GetSMSPayAPIResponse> call = api.GetSmsPayAPI_RES_CALL(request);

                        call.enqueue(new Callback<GetSMSPayAPIResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Response<GetSMSPayAPIResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        getMvpView().onSuccessSmsPayTransaction(response.body());
                                        // PaytmId = response.body().getPaytmId();
                                    } else {
                                        getMvpView().onFailedSmsPayTransaction(response.body());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });
                    } else {
                        getMvpView().onError("Internet Connection Not Available");
                    }

                }

            }
        });
        //Validate Link Button functionality.......
        smsPaymentDialog.setValidateLinkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsPaymentDialog.isValidateAmount()) {
                    GetSMSPayAPIRequest request = new GetSMSPayAPIRequest();
                    request.setOperationType("REDEEM");
                    request.setTransactionId(smsPaymentDialog.getTransactionId());
                    request.setCorpCode(getMvpView().getCalculatedPosTransactionRes().getCorpCode());
                    request.setRequestType("PAYTM");
                    request.setAmount(smsPaymentDialog.getWalletAmount());
                    request.setOrderNumnber(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                    request.setCustomerContactNumber(smsPaymentDialog.getWalletMobileNumber());
                    request.setCustomerName(getMvpView().getCalculatedPosTransactionRes().getCustomerName());
                    request.setCustomerEmail("");
                    request.setIsHBP(getDataManager().getGlobalJson().isISHBPStore());
                    request.setStoreId(getMvpView().getCalculatedPosTransactionRes().getStore());
                    try {
                        if (getMvpView().getGetSMSPayAPIResponse().equals(null)) {
                            request.setPaytmId("");

                        } else {
                            request.setPaytmId(getMvpView().getGetSMSPayAPIResponse().getPaytmId());
                        }
                    } catch (NullPointerException nullPointer) {
                        //log(causeStr, nullPointer, System.out);
                    }

                    request.setStatus(false);
                    request.setMessage("");
                    request.setOrderId(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                    request.setURL(getDataManager().getGlobalJson().SMSPayURL());
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<GetSMSPayAPIResponse> call = api.GetSmsPayAPI_RES_CALL(request);

                        call.enqueue(new Callback<GetSMSPayAPIResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Response<GetSMSPayAPIResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        getMvpView().onSuccessSmsPayValidateTransaction(response.body());
                                        if (response.body().getStatus() == true && response.body().getMessage().equalsIgnoreCase("SUCCESS")) {
                                            smsPaymentDialog.dismiss();
                                            generateTenterLineService(Double.parseDouble(smsPaymentDialog.getWalletAmount()), null);
                                        } else {
                                            getMvpView().onFailedSmsPayTransaction(response.body());
                                        }
                                        // PaytmId = response.body().getPaytmId();
                                    } else {
                                        getMvpView().onFailedSmsPayValidateTransaction(response.body());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });
                    } else {

                        getMvpView().onError("Internet Connection Not Available");

                    }
                }
            }
        });

        //cancel button functionality.............
        smsPaymentDialog.setCanceltransactionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsPaymentDialog.isValidateAmount()) {
                    GetSMSPayAPIRequest request = new GetSMSPayAPIRequest();
                    request.setOperationType("CANCEL");
                    request.setTransactionId(smsPaymentDialog.getTransactionId());
                    request.setCorpCode(getMvpView().getCalculatedPosTransactionRes().getCorpCode());
                    request.setRequestType("PAYTM");
                    request.setAmount(smsPaymentDialog.getWalletAmount());
                    request.setOrderNumnber(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                    request.setCustomerContactNumber(smsPaymentDialog.getWalletMobileNumber());
                    request.setCustomerName(getMvpView().getCalculatedPosTransactionRes().getCustomerName());
                    request.setCustomerEmail("");
                    request.setIsHBP(getDataManager().getGlobalJson().isISHBPStore());
                    request.setStoreId(getMvpView().getCalculatedPosTransactionRes().getStore());
                    // request.setPaytmId(getMvpView().getGetSMSPayAPIResponse().getPaytmId());
                    //  System.out.println("paytmid value-->"+String.valueOf(getMvpView().getGetSMSPayAPIResponse().getPaytmId().toString()));
                    // request.setPaytmId("");
                    try {
                        if (getMvpView().getGetSMSPayAPIResponse().equals(null)) {
                            request.setPaytmId("");

                        } else {
                            request.setPaytmId(getMvpView().getGetSMSPayAPIResponse().getPaytmId());
                        }
                    } catch (NullPointerException nullPointer) {
                        //log(causeStr, nullPointer, System.out);
                    }

                    request.setStatus(false);
                    request.setMessage("");
                    request.setOrderId(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                    request.setURL(getDataManager().getGlobalJson().SMSPayURL());
                    //  System.out.println("CancelTransaction method-->" + request);
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<GetSMSPayAPIResponse> call = api.GetSmsPayAPI_RES_CALL(request);
                        call.enqueue(new Callback<GetSMSPayAPIResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Response<GetSMSPayAPIResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getStatus() == true) {
                                            smsPaymentDialog.dismiss();
                                            getMvpView().onSuccessSmsPayCancelTransaction(response.body());
                                            // Toast.makeText(,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                        } else {
                                            getMvpView().onFailedSmsPayTransaction(response.body());
                                        }

                                    } else {
                                        getMvpView().onFailedSmsPayValidateTransaction(response.body());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<GetSMSPayAPIResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });
                    } else {
                        getMvpView().onError("Internet Connection Not Available");
                    }
                }
            }
        });

        smsPaymentDialog.show();
    }


    //HDFC payment dialog
    HdfcPaymentDialog hdfcPaymentDialog;
    private boolean isHdfcLinkGenerated = false;

    @Override
    public void showHdfcPaymentDialog() {
        String tenderurl = "";
        if (getDataManager().getTenderTypeResultEntity().get_TenderType().size() > 0) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTenderTypeId().equalsIgnoreCase("34")) {
                    tenderurl = tenderTypeEntity.getTenderURL();
                }
            }
        }
        hdfcPaymentDialog = new HdfcPaymentDialog(getMvpView().getContext());

        hdfcPaymentDialog.setCalculatedPosTransaction(getMvpView().getCalculatedPosTransactionRes());
        hdfcPaymentDialog.setPaymentMethod(getMvpView().getPaymentMethod());
        hdfcPaymentDialog.setDialogGenerateLinkBtnEnable();
        hdfcPaymentDialog.setWalletAmountEnable();
        hdfcPaymentDialog.setDialogCloseEnable();
        hdfcPaymentDialog.setCloseListener(v -> hdfcPaymentDialog.dismiss());

        String finalTenderurl = tenderurl;
        hdfcPaymentDialog.setGenerateLinkListener(v -> {
            if (hdfcPaymentDialog.isValidateAmount()) {
                if (getMvpView().isNetworkConnected()) {
                    getMvpView().showLoading();
                    HdfcLinkGenerateRequest hdfcLinkGenerateRequest = new HdfcLinkGenerateRequest();
                    hdfcLinkGenerateRequest.setUrl(finalTenderurl);
                    hdfcLinkGenerateRequest.setRequestType("GENOTP");
                    hdfcLinkGenerateRequest.setCustName(getMvpView().getCalculatedPosTransactionRes().getCustomerName());
                    hdfcLinkGenerateRequest.setCustEmailID("");
                    hdfcLinkGenerateRequest.setCustMobileNo(hdfcPaymentDialog.getWalletMobileNumber());
                    hdfcLinkGenerateRequest.setPayAmount(Double.valueOf(hdfcPaymentDialog.getWalletAmount()));
                    hdfcLinkGenerateRequest.setSiteID(getDataManager().getStoreId());
                    hdfcLinkGenerateRequest.setTerminalID(getDataManager().getTerminalId());
                    hdfcLinkGenerateRequest.setDocumentNo(getDataManager().getStoreId() + hdfcPaymentDialog.getTransactionId());
                    hdfcLinkGenerateRequest.setOrderID(hdfcPaymentDialog.getTransactionId());
                    hdfcLinkGenerateRequest.setTransactionMerchantID(null);
                    ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                    Call<HdfcLinkGenerateResponse> call = api.HDFC_LINK_GENERATE_RESPONSE_API_CALL(hdfcLinkGenerateRequest);
                    call.enqueue(new Callback<HdfcLinkGenerateResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<HdfcLinkGenerateResponse> call, @NotNull Response<HdfcLinkGenerateResponse> response) {
                            if (response.isSuccessful()) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.body().getErrorCode().equals("0")) {
                                        isHdfcLinkGenerated = true;
                                        hdfcPaymentDialog.setDialogGenerateLinkBtnDisable();
                                        hdfcPaymentDialog.setWalletAmountdisable();
//                                        hdfcPaymentDialog.setDialogClosedisable();
                                    }
                                    getMvpView().onSuccessHdfcPaymentListGenerateApi(response.body());
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<HdfcLinkGenerateResponse> call, @NotNull Throwable t) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                            handleApiError(t);
                        }
                    });
                } else {
                    getMvpView().onError("Internet Connection Not Available");
                }
            }

        });
        //Validate Link Button functionality.......
        hdfcPaymentDialog.setValidateLinkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isHdfcLinkGenerated) {
                if (hdfcPaymentDialog.isValidateAmount()) {
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        HdfcLinkGenerateRequest hdfcLinkGenerateRequest = new HdfcLinkGenerateRequest();
                        hdfcLinkGenerateRequest.setUrl(finalTenderurl);
                        hdfcLinkGenerateRequest.setRequestType("CHECKPAYMENTSTATUS");
                        hdfcLinkGenerateRequest.setCustName(getMvpView().getCalculatedPosTransactionRes().getCustomerName());
                        hdfcLinkGenerateRequest.setCustEmailID("");
                        hdfcLinkGenerateRequest.setCustMobileNo(hdfcPaymentDialog.getWalletMobileNumber());
                        hdfcLinkGenerateRequest.setPayAmount(Double.valueOf(hdfcPaymentDialog.getWalletAmount()));
                        hdfcLinkGenerateRequest.setSiteID(getDataManager().getStoreId());
                        hdfcLinkGenerateRequest.setTerminalID(getDataManager().getTerminalId());
                        hdfcLinkGenerateRequest.setDocumentNo(getDataManager().getStoreId() + hdfcPaymentDialog.getTransactionId());
                        hdfcLinkGenerateRequest.setOrderID(hdfcPaymentDialog.getTransactionId());
                        hdfcLinkGenerateRequest.setTransactionMerchantID(getMvpView().getHdfcTransactionId());
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<HdfcLinkGenerateResponse> call = api.HDFC_LINK_GENERATE_RESPONSE_API_CALL(hdfcLinkGenerateRequest);
                        call.enqueue(new Callback<HdfcLinkGenerateResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<HdfcLinkGenerateResponse> call, @NotNull Response<HdfcLinkGenerateResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        getMvpView().onSuccessHdfcPaymentListGenerateApi(response.body());
                                        if (response.body().getErrorCode().equals("0")) {
                                            hdfcPaymentDialog.dismiss();
                                            generateTenterLineService(Double.parseDouble(hdfcPaymentDialog.getWalletAmount()), null);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<HdfcLinkGenerateResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });
                    } else {
                        getMvpView().onError("Internet Connection Not Available");
                    }
                }
//                }else {
//                    Toast.makeText(getMvpView().getContext(), "Generate hdfc link before validate", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        //cancel button functionality.............
        hdfcPaymentDialog.setCanceltransactionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        hdfcPaymentDialog.show();

    }


    //Phonepe QrCode payment Dialog.............................
    PhonepeQrPaymentDialog phonepeQrPaymentDialog;

    private void showWalletPhonepeQrcodePaymentDialog(String title, boolean isEnableGenerateOtp, WalletServiceReq walletServiceReq, String tenderurl) {
//        String tenderurl = "";
//        if (Singletone.getInstance().tenderTypeResultEntity.get_TenderType() != null) {
//            for (GetTenderTypeRes._TenderTypeEntity typeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()) {
//                if (typeEntity.getTender().equalsIgnoreCase("QR Code")) {
//                    tenderurl = typeEntity.getTenderURL();
//                }
//            }
//        }
        phonepeQrPaymentDialog = new PhonepeQrPaymentDialog(getMvpView().getContext());
        // phonepeQrPaymentDialog.setTitle(title);
        //  walletPaymentDialog.setEnableGenerateOTP(isEnableGenerateOtp);
        phonepeQrPaymentDialog.setCalculatedPosTransaction(getMvpView().getCalculatedPosTransactionRes());
        phonepeQrPaymentDialog.setPaymentMethod(getMvpView().getPaymentMethod());
        phonepeQrPaymentDialog.ValidatePaymentStatussetdisabled();
        String providerReferenceId = "";
        phonepeQrPaymentDialog.setGenerateQrCodeEnable();
        phonepeQrPaymentDialog.setValidatePaymentDisable();
        phonepeQrPaymentDialog.setCancelPaymentDisable();
        phonepeQrPaymentDialog.setCloseBtnEnable();
        phonepeQrPaymentDialog.setGenerateQrcodeListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // walletServiceReq.setWalletRequestType(4);
                // generateWalletOTP(walletServiceReq);

                PhonepeGenerateQrCodeRequest request = new PhonepeGenerateQrCodeRequest();
                request.setReqType("GENERATEQRCODE");
                request.setUrl(tenderurl);
                request.setStoreId(getDataManager().getGlobalJson().getStoreID());
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("mmss", Locale.ENGLISH);
                Date myDate = new Date();
                String date = timeStampFormat.format(myDate);
                String transactionid = null;
                if (getMvpView().isOnleneOrder()) {
                    transactionid = getDataManager().getGlobalJson().getStoreID() + getMvpView().getOnlineTransactionId() + date;
                } else {
                    transactionid = getDataManager().getGlobalJson().getStoreID() + getMvpView().getTransactionModule().getTransactionID() + date;
                }
                Constant.getInstance().originaltransactionid = transactionid;
                request.setTransactionId(transactionid);
//                request.setTransactionId(getMvpView().getTransactionModule().getTransactionID());
                request.setAmount(phonepeQrPaymentDialog.getWalletAmount());
                request.setExpiresIn(2000);
                request.setProviderReferenceId("");
                request.setMessage("");
                request.setOriginalTransactionId("");
                if (getMvpView().isNetworkConnected()) {
                    getMvpView().showLoading();
                    ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                    Call<PhonepeGenerateQrCodeResponse> call = api.PhonepeQrCodeGenerateApi(request);
                    call.enqueue(new Callback<PhonepeGenerateQrCodeResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Response<PhonepeGenerateQrCodeResponse> response) {
                            if (response.isSuccessful()) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.body().getStatus() == true) {
                                        // phonepeQrPaymentDialog.dismiss();

                                        phonepeQrPaymentDialog.setGenerateQrCodeDisable();
                                        phonepeQrPaymentDialog.setValidatePaymentEnable();
                                        phonepeQrPaymentDialog.setCancelPaymentEnable();
                                        phonepeQrPaymentDialog.setCloseBtnDisable();
                                        phonepeQrPaymentDialog.setproviderReferenceId((String) response.body().getProviderReferenceId());
                                        phonepeQrPaymentDialog.ValidatePaymentStatussetenabled();

                                        phonepeQrPaymentDialog.setQrcodeimage(response.body().getQrCode());

                                        getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                    } else {
                                        getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                    }

                                } else {
                                    getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                    ;
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Throwable t) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                            handleApiError(t);
                        }
                    });

                } else {
                    getMvpView().onError("Internet Connection Not Available");
                }


                //   phonepeQrPaymentDialog.setQrcodeimage("Apollo Hospital EnterpriceLimited");

            }
        });
        phonepeQrPaymentDialog.setValidatePaymentStatusListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonepeQrPaymentDialog.isValidateAmount(walletServiceReq)) {
                    //  walletServiceReq.setMobileNo(phonepeQrPaymentDialog.getWalletMobileNumber());
                    //  walletServiceReq.setWalletAmount(phonepeQrPaymentDialog.getWalletAmount());
                    //  walletServiceReq.setWalletRequestType(3);
                    // generateWalletOTP(walletServiceReq);

                    PhonepeGenerateQrCodeRequest request = new PhonepeGenerateQrCodeRequest();
                    request.setReqType("CHECKPAYMENTSTATUS");
                    request.setUrl(tenderurl);
                    request.setStoreId(getDataManager().getGlobalJson().getStoreID());
                    request.setTransactionId(Constant.getInstance().originaltransactionid);
//                    request.setTransactionId(getMvpView().getTransactionModule().getTransactionID());
                    request.setAmount(phonepeQrPaymentDialog.getWalletAmount());
                    request.setExpiresIn(2000);
                    request.setProviderReferenceId(phonepeQrPaymentDialog.getProviderReferenceId());
                    request.setMessage("");
                    request.setOriginalTransactionId("");
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<PhonepeGenerateQrCodeResponse> call = api.PhonepeQrCodeGenerateApi(request);
                        call.enqueue(new Callback<PhonepeGenerateQrCodeResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Response<PhonepeGenerateQrCodeResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getStatus() == true) {
                                            //getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                            phonepeQrPaymentDialog.setGenerateQrCodeDisable();
                                            phonepeQrPaymentDialog.setValidatePaymentDisable();
                                            phonepeQrPaymentDialog.setCancelPaymentDisable();
                                            phonepeQrPaymentDialog.setCloseBtnEnable();
                                            walletServiceReq.setMobileNo(phonepeQrPaymentDialog.getWalletMobileNumber());
                                            walletServiceReq.setWalletAmount(phonepeQrPaymentDialog.getWalletAmount());
                                            walletServiceReq.setWalletRequestType(5);
                                            Constant.getInstance().PhonepeQrcode_transactionid = (String) response.body().getProviderReferenceId();
                                            walletServiceReq.setWalletOrderID((String) response.body().getProviderReferenceId());
//                                            generateWalletOTP(walletServiceReq);
                                            generateTenterLineService(phonepeQrPaymentDialog.getWalletAmount(), null);
                                            phonepeQrPaymentDialog.dismiss();

                                        } else {
                                            getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                        }

                                    } else {
                                        getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                        ;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });

                    } else {
                        getMvpView().onError("Internet Connection Not Available");
                    }


                }
            }
        });
        phonepeQrPaymentDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phonepeQrPaymentDialog.isValidateAmount(walletServiceReq)) {
                    //  walletServiceReq.setMobileNo(phonepeQrPaymentDialog.getWalletMobileNumber());
                    //  walletServiceReq.setWalletAmount(phonepeQrPaymentDialog.getWalletAmount());
                    //  walletServiceReq.setWalletRequestType(3);
                    // generateWalletOTP(walletServiceReq);

                    PhonepeGenerateQrCodeRequest request = new PhonepeGenerateQrCodeRequest();
                    request.setReqType("PAYMENTCANCEL");
                    request.setUrl(tenderurl);
                    request.setStoreId(getDataManager().getGlobalJson().getStoreID());
                    request.setTransactionId(Constant.getInstance().originaltransactionid);
//                    request.setTransactionId(getMvpView().getTransactionModule().getTransactionID());
                    request.setAmount(phonepeQrPaymentDialog.getWalletAmount());
                    request.setExpiresIn(2000);
                    request.setProviderReferenceId(phonepeQrPaymentDialog.getProviderReferenceId());
                    request.setMessage("");
                    request.setOriginalTransactionId("");
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showLoading();
                        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                        Call<PhonepeGenerateQrCodeResponse> call = api.PhonepeQrCodeGenerateApi(request);
                        call.enqueue(new Callback<PhonepeGenerateQrCodeResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Response<PhonepeGenerateQrCodeResponse> response) {
                                if (response.isSuccessful()) {
                                    //Dismiss Dialog
                                    getMvpView().hideLoading();
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getStatus() == true) {
                                            //getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                            phonepeQrPaymentDialog.setGenerateQrCodeEnable();
                                            phonepeQrPaymentDialog.setValidatePaymentDisable();
                                            phonepeQrPaymentDialog.setCancelPaymentDisable();
                                            phonepeQrPaymentDialog.setCloseBtnEnable();

                                            phonepeQrPaymentDialog.setQrcodeimage("REMOVE_QR_CODE");

                                        } else {
                                            getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                        }

                                    } else {
                                        getMvpView().onSuccessPhonepeGenerateQrCode(response.body());
                                        ;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<PhonepeGenerateQrCodeResponse> call, @NotNull Throwable t) {
                                //Dismiss Dialog
                                getMvpView().hideLoading();
                                handleApiError(t);
                            }
                        });

                    } else {
                        getMvpView().onError("Internet Connection Not Available");
                    }


                }

            }
        });

        phonepeQrPaymentDialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonepeQrPaymentDialog.dismiss();
            }
        });
        phonepeQrPaymentDialog.show();
    }


    WalletPaymentDialog walletPaymentDialog;

    private void showWalletPaymentDialog(String title, boolean isEnableGenerateOtp, WalletServiceReq walletServiceReq) {
        walletPaymentDialog = new WalletPaymentDialog(getMvpView().getContext());
        walletPaymentDialog.setTitle(title);
        walletPaymentDialog.setEnableGenerateOTP(isEnableGenerateOtp);
        walletPaymentDialog.setCalculatedPosTransaction(getMvpView().getCalculatedPosTransactionRes());
        walletPaymentDialog.setPaymentMethod(getMvpView().getPaymentMethod());
        walletPaymentDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletServiceReq.setWalletRequestType(4);
                generateWalletOTP(walletServiceReq);

            }
        });
        walletPaymentDialog.setGenerateOTPListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletPaymentDialog.isValidateAmount(walletServiceReq)) {
                    //walletPaymentDialog.dismiss();
                    walletServiceReq.setMobileNo(walletPaymentDialog.getWalletMobileNumber());
                    walletServiceReq.setWalletAmount(walletPaymentDialog.getWalletAmount());
                    walletServiceReq.setWalletRequestType(3);
                    generateWalletOTP(walletServiceReq);
                }
            }
        });
        walletPaymentDialog.setValidateOTPListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletPaymentDialog.isValidateOTP(walletServiceReq)) {
                    // walletPaymentDialog.dismiss();
                    walletServiceReq.setMobileNo(walletPaymentDialog.getWalletMobileNumber());
                    walletServiceReq.setWalletAmount(walletPaymentDialog.getWalletAmount());
                    walletServiceReq.setOTP(walletPaymentDialog.getOTPFieldData());
                    walletServiceReq.setWalletRequestType(0);
                    generateWalletOTP(walletServiceReq);
                }

            }
        });
        walletPaymentDialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletPaymentDialog.dismiss();
            }
        });
        walletPaymentDialog.show();
    }

    private List<SalesLineEntity> salesLineEntityData = new ArrayList<SalesLineEntity>();
//    private CalculatePosTransactionRes calculatePosTransactionResData=new CalculatePosTransactionRes();

    private void saveRetailTransaction() {
        CalculatePosTransactionRes posTransactionRes = getMvpView().getCalculatedPosTransactionRes();
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
//            CalculatePosTransactionRes posTransactionRes = getMvpView().getCalculatedPosTransactionRes();
            ArrayList<SalesLineEntity> salesLineEntities = new ArrayList<>();
            for (SalesLineEntity salesLineEntity : posTransactionRes.getSalesLine()) {
                if (!salesLineEntity.getIsVoid()) {
                    salesLineEntities.add(salesLineEntity);
                }
            }
            posTransactionRes.setSalesLine(salesLineEntities);

            List<TenderLineEntity> tenderLineEntitieList = new ArrayList<>();
            for (TenderLineEntity tenderLineEntity : posTransactionRes.getTenderLine()) {
                if (tenderLineEntity.getTenderName().equalsIgnoreCase("PhonePe") || tenderLineEntity.getTenderName().equalsIgnoreCase("PAYTM") || tenderLineEntity.getTenderName().equalsIgnoreCase("Airtel") || tenderLineEntity.getTenderName().equalsIgnoreCase("Pay through QR Code") || tenderLineEntity.getTenderName().equalsIgnoreCase("QR Code")) {
                    tenderLineEntity.setMobileNo(tenderLineEntity.getMobileNo());
                    tenderLineEntitieList.add(tenderLineEntity);
                } else if (tenderLineEntity.getTenderName().equalsIgnoreCase("Cash") || tenderLineEntity.getTenderName().equalsIgnoreCase("card") || tenderLineEntity.getTenderName().equalsIgnoreCase("gift") || tenderLineEntity.getTenderName().equalsIgnoreCase("Credit") || tenderLineEntity.getTenderName().equalsIgnoreCase("COD")) {
                    tenderLineEntity.setMobileNo("");
                    tenderLineEntitieList.add(tenderLineEntity);
                } else if (tenderLineEntity.getTenderName().equalsIgnoreCase("SMS PAY")) {
                    tenderLineEntity.setMobileNo(tenderLineEntity.getMobileNo());
                    tenderLineEntitieList.add(tenderLineEntity);
                } else if (tenderLineEntity.getTenderName().equalsIgnoreCase("HDFC PAYMENT")) {
                    tenderLineEntity.setMobileNo(tenderLineEntity.getMobileNo());
                    tenderLineEntitieList.add(tenderLineEntity);
                }

            }
            posTransactionRes.setTrackingRef(getMvpView().getPrgTracking());
            posTransactionRes.setTenderLine(tenderLineEntitieList);
//            calculatePosTransactionResData = posTransactionRes;

            //  Gson gson=new Gson();
            //   String json=gson.toJson(posTransactionRes);
            //  System.out.println("void data"+json);

            Call<SaveRetailsTransactionRes> call = api.SAVE_RETAILS_TRANSACTION_RES_CALL(posTransactionRes);

            call.enqueue(new Callback<SaveRetailsTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<SaveRetailsTransactionRes> call, @NotNull Response<SaveRetailsTransactionRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().onSuccessSaveRetailTransaction(response.body());
                        else getMvpView().onFailedSaveRetailsTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SaveRetailsTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    // Generate TenderLine Request Formation
    private GenerateTenderLineReq generateTenderLineReq(double amount, WalletServiceRes walletServiceRes) {
        GenerateTenderLineReq tenderLineReq = new GenerateTenderLineReq();
        tenderLineReq.setType(typeEntity());
        tenderLineReq.setPOSTransaction(getMvpView().getCalculatedPosTransactionRes());

        if (Constant.getInstance().isomsorder) {
            tenderLineReq.setWallet(null);
        } else {
            tenderLineReq.setWallet(wallet(amount, walletServiceRes));

        }

        return tenderLineReq;
    }


    private TypeEntity typeEntity() {
        TypeEntity typeEntity = new TypeEntity();

        if (getDataManager().getTenderTypeResultEntity() != null && getDataManager().getTenderTypeResultEntity().get_TenderType().size() > 0) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : getDataManager().getTenderTypeResultEntity().get_TenderType()) {
                //  System.out.println("TenderName-->" + tenderTypeEntity.getTender());

                if (getMvpView().getPaymentMethod().isCashMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("Cash")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isCardMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("card")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());


                    }
                } else if (getMvpView().getPaymentMethod().isSmsPayMode()) {
                    //   System.out.println("TenderName-->" + tenderTypeEntity.getTender());

                    if (tenderTypeEntity.getTender().equalsIgnoreCase("SMS PAY")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isOneApolloMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("gift")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isCreditMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("Credit")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isVendorPayMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("Credit")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isPhonePeMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("PhonePe")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isPhonePeQrMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("Pay through QR Code") || tenderTypeEntity.getTender().equalsIgnoreCase("QR Code")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isPaytmMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("PAYTM")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isAirtelMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("Airtel")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                } else if (getMvpView().getPaymentMethod().isCodPayMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("COD")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());


                    }
                } else if (getMvpView().getPaymentMethod().isHdfcPayMode()) {
                    if (tenderTypeEntity.getTender().equalsIgnoreCase("HDFC PAYMENT")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());


                    }
                }
            }
        }

        return typeEntity;
    }

    private POSTransactionEntity posTransactionEntityData = new POSTransactionEntity();

    private POSTransactionEntity posTransactionEntity() {
        POSTransactionEntity posTransactionEntity = new POSTransactionEntity();
        posTransactionEntity.setAmounttoAccount(0);
        posTransactionEntity.setBatchTerminalid("");
        posTransactionEntity.setBillingCity("");
        posTransactionEntity.setBusinessDate(CommonUtils.getCurrentDate(CommonUtils.DATE_FORMAT_DD_MMM_YYYY));
        posTransactionEntity.setCancelReasonCode("");
        posTransactionEntity.setChannel(getDataManager().getGlobalJson().getChannel());
        posTransactionEntity.setComment("");
        // Log.d("Corporate code-->", getMvpView().getCorporateModule().getCode());
        posTransactionEntity.setCorpCode(getMvpView().getCorporateModule().getCode());
        posTransactionEntity.setCouponCode("");
        posTransactionEntity.setCreatedonPosTerminal(getDataManager().getTerminalId());// Terminal Id
        posTransactionEntity.setCurrency(getDataManager().getGlobalJson().getCurrency());
        posTransactionEntity.setCurrentSalesLine(0);
        posTransactionEntity.setCustAccount("");
        posTransactionEntity.setCustAddress("");
        posTransactionEntity.setCustDiscamount(0);
        posTransactionEntity.setCustomerID(getMvpView().getCustomerModule().getCustId());// customer Id
        posTransactionEntity.setCustomerName(getMvpView().getCustomerModule().getCardName());
        posTransactionEntity.setCustomerState("");
        posTransactionEntity.setDataAreaId(getDataManager().getDataAreaId()); // DataAreId
        posTransactionEntity.setDeliveryDate("");
        posTransactionEntity.setDiscAmount(0);
        posTransactionEntity.setDOB("");
        posTransactionEntity.setDoctorCode(getMvpView().getDoctorModule() != null ? getMvpView().getDoctorModule().getCode() : "");
        posTransactionEntity.setDoctorName(getMvpView().getDoctorModule() != null ? getMvpView().getDoctorModule().getDisplayText() : "");
        posTransactionEntity.setEntryStatus(0);
        posTransactionEntity.setGender(0);
        posTransactionEntity.setGrossAmount(getMvpView().getOrderPriceInfoModel().getMrpTotalAmount());//gross Amount
        posTransactionEntity.setIPNO("");
        posTransactionEntity.setIPSerialNO("");
        posTransactionEntity.setISAdvancePayment(getDataManager().getGlobalJson().isISAdvancePaymentAllowed());
        posTransactionEntity.setISBatchModifiedAllowed(false);
        posTransactionEntity.setISPosted(false);
        posTransactionEntity.setISPrescibeDiscount(false);
        posTransactionEntity.setISReserved(false);
        posTransactionEntity.setISReturnAllowed(false);
        posTransactionEntity.setManualBill(Singletone.getInstance().isManualBilling);
        posTransactionEntity.setMobileNO(getMvpView().getCustomerModule().getMobileNo());// user mobile number
        posTransactionEntity.setNetAmount(getMvpView().getOrderPriceInfoModel().getTaxableTotalAmount());// net amount
        posTransactionEntity.setNetAmountInclTax(getMvpView().getOrderPriceInfoModel().getMrpTotalAmount());// include tax total
        posTransactionEntity.setNumberofItemLines(getMvpView().getSelectedProducts().size());// items count
        posTransactionEntity.setNumberofItems(getMvpView().getSelectedProducts().size());// items count
        posTransactionEntity.setOrderSource("");
        posTransactionEntity.setOrderType("");
        posTransactionEntity.setPaymentSource("");
        posTransactionEntity.setPincode("");
        posTransactionEntity.setPosEvent(0);
        posTransactionEntity.setReciptId("");
        posTransactionEntity.setREFNO("");
        posTransactionEntity.setRegionCode("");
        posTransactionEntity.setRemainingamount(getMvpView().orderRemainingAmount());// remaning amount
        posTransactionEntity.setReminderDays(0);
        posTransactionEntity.setRequestStatus(0);
        posTransactionEntity.setReturn(false);
        posTransactionEntity.setReturnMessage("");
        posTransactionEntity.setReturnReceiptId("");
        posTransactionEntity.setReturnStore("");
        posTransactionEntity.setReturnTerminal("");
        posTransactionEntity.setReturnTransactionId("");
        posTransactionEntity.setReturnType(0);
        posTransactionEntity.setRoundedAmount(0);
        posTransactionEntity.setSalesLine(getMvpView().getSelectedProducts());// sales line object
        posTransactionEntity.setSalesOrigin(getMvpView().getSalesOrigin());
        posTransactionEntity.setSEZ(0);
        posTransactionEntity.setShippingMethod("");
        posTransactionEntity.setStaff(getDataManager().getUserId());// terminal Id
        posTransactionEntity.setState(getDataManager().getGlobalJson().getStateCode());
        posTransactionEntity.setStockCheck(!Singletone.getInstance().isManualBilling);
        posTransactionEntity.setStore(getDataManager().getStoreId());// store details
        posTransactionEntity.setStoreName(getDataManager().getGlobalJson().getStoreName());
        posTransactionEntity.setTenderLine(tenderLineEntities.getTenderLine());// TenderLine Object
        posTransactionEntity.setTerminal(getDataManager().getTerminalId());// teinal id
        posTransactionEntity.setTimewhenTransClosed(0);
        posTransactionEntity.setTotalDiscAmount(0);
        posTransactionEntity.setTotalManualDiscountAmount(0);
        posTransactionEntity.setTotalManualDiscountPercentage(0);
        posTransactionEntity.setTotalMRP(getMvpView().getOrderPriceInfoModel().getMrpTotalAmount());// total MRP Price
        posTransactionEntity.setTotalTaxAmount(getMvpView().getOrderPriceInfoModel().getTaxableTotalAmount());// total Tax amount
        posTransactionEntity.setTrackingRef(getMvpView().getCorporateModule().getPrg_Tracking());
        if (getMvpView().isOnleneOrder()) {
            posTransactionEntity.setISOnlineOrder(true);
            posTransactionEntity.setTransactionId(getMvpView().getOnlineTransactionId());// Trancaction id
        } else {
            posTransactionEntity.setISOnlineOrder(false);
            posTransactionEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());// Trancaction id
        }
        posTransactionEntity.setTransDate(CommonUtils.getCurrentDate("dd-MMM-YYYY hh:mm:ss"));// payment date time format `18-Feb-2020 03:01:20`
        posTransactionEntity.setTransType(0);
        posTransactionEntity.setType(2);
        posTransactionEntity.setVendorId("");
        //  System.out.println("isoms order status-->"+Constant.getInstance().isomsorder);
        if (getMvpView().isOnleneOrder()) {
            posTransactionEntity.setISOMSOrder(false);
            posTransactionEntity.setBillingCity(getMvpView().getUnPostedTransactionResponseBody().getBillingCity());
            posTransactionEntity.setCurrentSalesLine(0);
            posTransactionEntity.setCustAddress(getMvpView().getUnPostedTransactionResponseBody().getCustAddress());
            posTransactionEntity.setCustomerState(getMvpView().getUnPostedTransactionResponseBody().getCustomerState());
            posTransactionEntity.setDeliveryDate(getMvpView().getUnPostedTransactionResponseBody().getDeliveryDate());
            posTransactionEntity.setDOB(getMvpView().getUnPostedTransactionResponseBody().getDOB());
            double tempomsexpiry = getDataManager().getGlobalJson().getOMSExpiryDays();
            int omsexpiry = (int) tempomsexpiry;
            posTransactionEntity.setExpiryDays(omsexpiry);
            posTransactionEntity.setGender(1);
            posTransactionEntity.setISAdvancePayment(false);
            posTransactionEntity.setISOMSValidate(true);
            posTransactionEntity.setOrderPrescriptionURL(getMvpView().getUnPostedTransactionResponseBody().getOrderPrescriptionURL());
            posTransactionEntity.setOrderSource(getMvpView().getUnPostedTransactionResponseBody().getOrderSource());
            posTransactionEntity.setOrderType(getMvpView().getUnPostedTransactionResponseBody().getOrderType());
            posTransactionEntity.setPaymentSource(getMvpView().getUnPostedTransactionResponseBody().getPaymentSource());
            posTransactionEntity.setPincode(getMvpView().getUnPostedTransactionResponseBody().getPincode());
            posTransactionEntity.setREFNO(getMvpView().getUnPostedTransactionResponseBody().getREFNO());
            posTransactionEntity.setSalesOrigin("8");
            posTransactionEntity.setShippingMethod(getMvpView().getUnPostedTransactionResponseBody().getShippingMethod());
            posTransactionEntity.setShippingMethodDesc(getMvpView().getUnPostedTransactionResponseBody().getShippingMethod());
            posTransactionEntity.setVendorId(getMvpView().getUnPostedTransactionResponseBody().getVendorId());
            posTransactionEntity.setCreatedonPosTerminal(getMvpView().getUnPostedTransactionResponseBody().getCreatedonPosTerminal());
            posTransactionEntity.setTerminal(getMvpView().getUnPostedTransactionResponseBody().getTerminal());
            // posTransactionEntity.setTransactionId(getMvpView().getCustomerDataResbean().getREFNO());
        } else if (Constant.getInstance().isomsorder) {
            posTransactionEntity.setISOMSOrder(true);
            posTransactionEntity.setBillingCity(getMvpView().getCustomerDataResbean().getBillingCity());
            posTransactionEntity.setCurrentSalesLine(0);
            posTransactionEntity.setCustAddress(getMvpView().getCustomerDataResbean().getCustAddress());
            posTransactionEntity.setCustomerState(getMvpView().getCustomerDataResbean().getCustomerState());
            posTransactionEntity.setDeliveryDate(getMvpView().getCustomerDataResbean().getDeliveryDate());
            posTransactionEntity.setDOB(getMvpView().getCustomerDataResbean().getDOB());
            double tempomsexpiry = getDataManager().getGlobalJson().getOMSExpiryDays();
            int omsexpiry = (int) tempomsexpiry;
            posTransactionEntity.setExpiryDays(omsexpiry);
            posTransactionEntity.setGender(1);
            posTransactionEntity.setISAdvancePayment(false);
            posTransactionEntity.setISOMSValidate(true);
            posTransactionEntity.setOrderPrescriptionURL(getMvpView().getCustomerDataResbean().getOrderPrescriptionURL());
            posTransactionEntity.setOrderSource(getMvpView().getCustomerDataResbean().getOrderSource());
            posTransactionEntity.setOrderType(getMvpView().getCustomerDataResbean().getOrderType());
            posTransactionEntity.setPaymentSource(getMvpView().getCustomerDataResbean().getPaymentSource());
            posTransactionEntity.setPincode(getMvpView().getCustomerDataResbean().getPincode());
            posTransactionEntity.setREFNO(getMvpView().getCustomerDataResbean().getREFNO());
            posTransactionEntity.setSalesOrigin("8");
            posTransactionEntity.setShippingMethod(getMvpView().getCustomerDataResbean().getShippingMethod());
            posTransactionEntity.setShippingMethodDesc(getMvpView().getCustomerDataResbean().getShippingMethod());
            posTransactionEntity.setVendorId(getMvpView().getCustomerDataResbean().getVendorId());
            posTransactionEntity.setCreatedonPosTerminal(getMvpView().getCustomerDataResbean().getCreatedonPosTerminal());
            posTransactionEntity.setTerminal(getMvpView().getCustomerDataResbean().getTerminal());
            // posTransactionEntity.setTransactionId(getMvpView().getCustomerDataResbean().getREFNO());


        } else {
            posTransactionEntity.setISOMSOrder(false);

        }


        posTransactionEntityData = posTransactionEntity;
        return posTransactionEntity;
    }

    public void omsaddnewitem(ArrayList<SalesLineEntity> itemsArrayList) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();

            double qty = 0;
            for (SalesLineEntity entity : itemsArrayList) {
                qty = qty + entity.getQty();
            }

            String omsurl = getDataManager().getGlobalJson().getOMSUrl();
            if (omsurl.endsWith("/")) {

            } else {
                omsurl = omsurl + "/";
            }

            ApiInterface api = ApiClient.getApiServiceOTp(omsurl);
            OmsAddNewItemRequest omsAddNewItemRequest = new OmsAddNewItemRequest();
            omsAddNewItemRequest.setFullfillmentOrderID(getMvpView().getCalculatedPosTransactionRes().getREFNO());
            omsAddNewItemRequest.setItemID(itemsArrayList.get(0).getItemId());
            omsAddNewItemRequest.setQty(qty);
            omsAddNewItemRequest.setDiscPer(0);
            omsAddNewItemRequest.setStockQty(itemsArrayList.get(0).getStockQty());
            omsAddNewItemRequest.setOmsLineID(0);
            omsAddNewItemRequest.setOmsLineRECID(0);
            omsAddNewItemRequest.setStoreID(getDataManager().getStoreId());
            omsAddNewItemRequest.setTerminalID(getTerminalId());
            omsAddNewItemRequest.setDataAreaID(getDataManager().getDataAreaId());
            omsAddNewItemRequest.setRequestStatus(0);
            omsAddNewItemRequest.setReturnMessage("");
            Call<OmsAddNewItemResponse> call = api.GET_OMS_ADD_New_item(omsAddNewItemRequest);
            call.enqueue(new Callback<OmsAddNewItemResponse>() {
                @Override
                public void onResponse(@NotNull Call<OmsAddNewItemResponse> call, @NotNull Response<OmsAddNewItemResponse> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().onSuccessOmsAddNewItem(response.body(), itemsArrayList);
                        else getMvpView().onFailedOmsAddNewItem(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OmsAddNewItemResponse> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


//    private ArrayList<SalesLineEntity> salesLineEntities() {
//        ArrayList<SalesLineEntity> salesLineEntities = new ArrayList<>();
//        ArrayList<GetItemDetailsRes.Items> itemsArrayList = getMvpView().getSelectedProducts();
//        for (int i = 0; i < itemsArrayList.size(); i++) {
//            GetItemDetailsRes.Items items = itemsArrayList.get(i);
//
//            SalesLineEntity salesLineEntity = new SalesLineEntity();
//            salesLineEntity.setAdditionaltax(0);
//            salesLineEntity.setApplyDiscount(false);
//            salesLineEntity.setBarcode("");
//            salesLineEntity.setBaseAmount(items.getBatchListObj().getMRP());
//            salesLineEntity.setCategory(items.getCategory());
//            salesLineEntity.setCategoryCode(items.getCategoryCode());
//            salesLineEntity.setCategoryReference("");
//            salesLineEntity.setCESSPerc(0);
//            salesLineEntity.setCESSTaxCode("");
//            salesLineEntity.setCGSTPerc(items.getBatchListObj().getCGSTPerc());
//            salesLineEntity.setCGSTTaxCode(items.getBatchListObj().getCGSTTaxCode());
//            salesLineEntity.setChecked(false);
//            salesLineEntity.setComment("");
//            salesLineEntity.setDiscAmount(0);
//            salesLineEntity.setDiscId("");
//            salesLineEntity.setDiscOfferId("");
//            salesLineEntity.setDiscountStructureType(0);
//            salesLineEntity.setDiscountType("");
//            salesLineEntity.setDiseaseType("Acute");
//            salesLineEntity.setDPCO(false);
//            salesLineEntity.setExpiry(items.getBatchListObj().getExpDate());
//            salesLineEntity.setHsncode_In(items.getHsncode_In());
//            salesLineEntity.setIGSTPerc(items.getBatchListObj().getIGSTPerc());
//            salesLineEntity.setIGSTTaxCode(items.getBatchListObj().getIGSTTaxCode());
//            salesLineEntity.setInventBatchId(items.getBatchListObj().getBatchNo());
//            salesLineEntity.setISPrescribed(0);
//            salesLineEntity.setISReserved(false);
//            salesLineEntity.setISStockAvailable(false);
//            salesLineEntity.setItemId(items.getArtCode());
//            salesLineEntity.setItemName(items.getDescription());
//            salesLineEntity.setLineDiscPercentage(0);
//            salesLineEntity.setLinedscAmount(0);
//            salesLineEntity.setLineManualDiscountAmount(0);
//            salesLineEntity.setLineManualDiscountPercentage(0);
//            salesLineEntity.setLineNo(i);
//            salesLineEntity.setManufacturerCode(items.getManufactureCode());
//            salesLineEntity.setManufacturerName(items.getManufacture());
//            salesLineEntity.setMixMode(false);
//            salesLineEntity.setMMGroupId("0");
//            salesLineEntity.setModifyBatchId("");
//            salesLineEntity.setMRP(items.getBatchListObj().getMRP());
//            salesLineEntity.setNetAmount(items.getBatchListObj().getPrice());
//            salesLineEntity.setNetAmountInclTax(items.getBatchListObj().getMRP());
//            salesLineEntity.setOfferAmount(0);
//            salesLineEntity.setOfferDiscountType(0);
//            salesLineEntity.setOfferDiscountValue(0);
//            salesLineEntity.setOfferQty(0);
//            salesLineEntity.setOfferType(0);
//            salesLineEntity.setOmsLineID(0);
//            salesLineEntity.setOmsLineRECID(0);
//            salesLineEntity.setOrderStatus(0);
//            salesLineEntity.setOriginalPrice(items.getBatchListObj().getMRP());
//            salesLineEntity.setPeriodicDiscAmount(0);
//            salesLineEntity.setPreviewText("");
//            salesLineEntity.setPrice(items.getBatchListObj().getPrice());
//            salesLineEntity.setPriceOverride(false);
//            salesLineEntity.setProductRecID(items.getProductRecID());
//            salesLineEntity.setQty(items.getBatchListObj().getEnterReqQuantity());
//            salesLineEntity.setRemainderDays(0);
//            salesLineEntity.setRemainingQty(0);
//            salesLineEntity.setRetailCategoryRecID(items.getRetailCategoryRecID());
//            salesLineEntity.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
//            salesLineEntity.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
//            salesLineEntity.setReturnQty(0);
//            salesLineEntity.setScheduleCategory(items.getSch_Catg());
//            salesLineEntity.setScheduleCategoryCode(items.getSch_Catg_Code());
//            salesLineEntity.setSGSTPerc(items.getBatchListObj().getSGSTPerc());
//            salesLineEntity.setSGSTTaxCode(items.getBatchListObj().getSGSTTaxCode());
//            salesLineEntity.setStockQty(0);
//            salesLineEntity.setSubCategory(items.getSubCategory());
//            salesLineEntity.setSubCategoryCode(items.getSubCategory());
//            salesLineEntity.setSubClassification(items.getSubClassification());
//            salesLineEntity.setSubsitute(false);
//            salesLineEntity.setSubstitudeItemId("");
//            salesLineEntity.setTax(items.getBatchListObj().getTotalTax());
//            salesLineEntity.setTaxAmount(items.getBatchListObj().getTotalTax());
//            salesLineEntity.setTotal(items.getBatchListObj().getMRP());
//            salesLineEntity.setTotalDiscAmount(0);
//            salesLineEntity.setTotalDiscPct(0);
//            salesLineEntity.setTotalRoundedAmount(0);
//            salesLineEntity.setTotalTax(items.getBatchListObj().getTotalTax());
//            salesLineEntity.setUnit("");
//            salesLineEntity.setUnitPrice(items.getBatchListObj().getMRP());
//            salesLineEntity.setUnitQty(items.getBatchListObj().getREQQTY());
//            salesLineEntity.setVariantId("");
//            if (items.isItemDelete()) {
//                salesLineEntity.setVoid(true);
//            } else {
//                salesLineEntity.setVoid(false);
//            }
//            salesLineEntities.add(salesLineEntity);
//
//        }
//        return salesLineEntities;
//    }

    private Wallet wallet(double amount, WalletServiceRes walletServiceRes) {
        Wallet wallet = new Wallet();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        if (walletServiceRes != null) {
            if (typeEntity().getTender().equalsIgnoreCase("Cash") || typeEntity().getTender().equalsIgnoreCase("card") || typeEntity().getTender().equalsIgnoreCase("gift") || typeEntity().getTender().equalsIgnoreCase("Credit")) {
                wallet.setMobileNo("");
            } else if (typeEntity().getTender().equalsIgnoreCase("PhonePe") || typeEntity().getTender().equalsIgnoreCase("PAYTM") || typeEntity().getTender().equalsIgnoreCase("Airtel") || typeEntity().getTender().equalsIgnoreCase("Pay through QR Code") || typeEntity().getTender().equalsIgnoreCase("QR Code")) {
                wallet.setMobileNo(walletServiceRes.getMobileNo());

            } else if (typeEntity().getTender().equalsIgnoreCase("SMS PAY")) {
                wallet.setMobileNo(smsPaymentDialog.getWalletMobileNumber());
            } else if (typeEntity().getTender().equalsIgnoreCase("HDFC PAYMENT")) {
                wallet.setMobileNo(hdfcPaymentDialog.getWalletMobileNumber());
            }
            wallet.setOTP(walletServiceRes.getOTP());
            wallet.setOTPTransactionId(walletServiceRes.getOTPTransactionId());
            wallet.setPOSTerminal(walletServiceRes.getPOSTerminal());
            wallet.setPOSTransactionID(walletServiceRes.getPOSTransactionID());
            wallet.setRequestStatus(walletServiceRes.getRequestStatus());
            wallet.setRequestURL(walletServiceRes.getRequestURL());
            wallet.setResponse(walletServiceRes.getResponse());
            wallet.setReturnMessage(walletServiceRes.getReturnMessage());
            wallet.setRewardsPoint(walletServiceRes.getRewardsPoint());
            wallet.setWalletAmount(walletServiceRes.getWalletAmount());
            wallet.setWalletOrderID(walletServiceRes.getWalletOrderID());
            wallet.setWalletRefundId(walletServiceRes.getWalletRefundId());
            wallet.setWalletRequestType(walletServiceRes.getWalletRequestType());
            wallet.setWalletTransactionID(walletServiceRes.getWalletTransactionID());

            if (typeEntity().getTender().equalsIgnoreCase("SMS PAY")) {
                wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
                wallet.setWalletOrderID(getMvpView().getGetSMSPayAPIResponse().getPaytmId());
            } else if (typeEntity().getTender().equalsIgnoreCase("HDFC PAYMENT")) {
                wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + hdfcPaymentDialog.getTransactionId());
                wallet.setWalletOrderID(getMvpView().getHdfcTransactionId());
            } else if (typeEntity().getTender().equalsIgnoreCase("card")) {
                wallet.setWalletTransactionID(Constant.getInstance().card_transaction_id);
                wallet.setWalletOrderID("EZETAP");
                if (getDataManager().getGlobalJson().isISHBPStore() && !getDataManager().getGlobalJson().isISEzetapActive()) {
                    wallet.setWalletTransactionID("");
                    wallet.setWalletOrderID("");
                }
            } else if (typeEntity().getTender().equalsIgnoreCase("Pay through QR Code") || typeEntity().getTender().equalsIgnoreCase("QR Code")) {
                if (getMvpView().isOnleneOrder()) {
                    wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getOnlineTransactionId() + String.valueOf(minutes) + String.valueOf(seconds));
                } else {
                    wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getTransactionModule().getTransactionID() + String.valueOf(minutes) + String.valueOf(seconds));
                }
                wallet.setWalletOrderID(Constant.getInstance().PhonepeQrcode_transactionid);
            }
        } else {
            if (typeEntity().getTender().equalsIgnoreCase("Cash") || typeEntity().getTender().equalsIgnoreCase("card") || typeEntity().getTender().equalsIgnoreCase("gift") || typeEntity().getTender().equalsIgnoreCase("Credit")) {
                wallet.setMobileNo("");
            } else if (typeEntity().getTender().equalsIgnoreCase("PhonePe") || typeEntity().getTender().equalsIgnoreCase("PAYTM") || typeEntity().getTender().equalsIgnoreCase("Airtel") || typeEntity().getTender().equalsIgnoreCase("Pay through QR Code") || typeEntity().getTender().equalsIgnoreCase("QR Code")) {
                wallet.setMobileNo(getMvpView().getCustomerModule().getMobileNo());
            } else if (typeEntity().getTender().equalsIgnoreCase("SMS PAY")) {
                wallet.setMobileNo(smsPaymentDialog.getWalletMobileNumber());
            } else if (typeEntity().getTender().equalsIgnoreCase("HDFC PAYMENT")) {
                wallet.setMobileNo(hdfcPaymentDialog.getWalletMobileNumber());
            }
            wallet.setOTP(getMvpView().getOneApolloOtp());
            wallet.setOTPTransactionId("");
            wallet.setPOSTerminal(getDataManager().getTerminalId());
            if (getMvpView().isOnleneOrder()) {
                wallet.setPOSTransactionID(getMvpView().getOnlineTransactionId());
            } else {
                wallet.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
            }
            wallet.setRequestStatus(0);
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setReturnMessage("");
            wallet.setRewardsPoint(0);
            wallet.setWalletAmount(amount);
            wallet.setWalletOrderID("");
            wallet.setWalletRefundId("");
            wallet.setWalletRequestType(0);
            if (getMvpView().getValidateOneApolloPoints() != null && getMvpView().getValidateOneApolloPoints().getRRNO() != null) {
                wallet.setWalletTransactionID(getMvpView().getValidateOneApolloPoints().getRRNO());
            }
            if (typeEntity().getTender().equalsIgnoreCase("card")) {

                wallet.setWalletTransactionID(Constant.getInstance().card_transaction_id);
                wallet.setWalletOrderID("EZETAP");
                if (getDataManager().getGlobalJson().isISHBPStore() && !getDataManager().getGlobalJson().isISEzetapActive()) {
                    wallet.setWalletTransactionID("");
                    wallet.setWalletOrderID("");
                }
            } else if (typeEntity().getTender().equalsIgnoreCase("Pay through QR Code") || typeEntity().getTender().equalsIgnoreCase("QR Code")) {
                if (getMvpView().isOnleneOrder()) {
                    wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getOnlineTransactionId() + String.valueOf(minutes) + String.valueOf(seconds));
                } else {
                    wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getTransactionModule().getTransactionID() + String.valueOf(minutes) + String.valueOf(seconds));
                }
                wallet.setWalletOrderID(Constant.getInstance().PhonepeQrcode_transactionid);
            } else {
                wallet.setWalletTransactionID("");
            }


        }
        if (typeEntity().getTender().equalsIgnoreCase("HDFC PAYMENT")) {
            wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + hdfcPaymentDialog.getTransactionId());
            wallet.setWalletOrderID(getMvpView().getHdfcTransactionId());
        }
        if (typeEntity().getTender().equalsIgnoreCase("SMS PAY")) {
            wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + smsPaymentDialog.getTransactionId());
            wallet.setWalletOrderID(getMvpView().getGetSMSPayAPIResponse().getPaytmId());
        }
        if (typeEntity().getTender().equalsIgnoreCase("Pay through QR Code") || typeEntity().getTender().equalsIgnoreCase("QR Code")) {
            if (getMvpView().isOnleneOrder()) {
                wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getOnlineTransactionId() + String.valueOf(minutes) + String.valueOf(seconds));
            } else {
                wallet.setWalletTransactionID(getMvpView().getCalculatedPosTransactionRes().getStore() + getMvpView().getTransactionModule().getTransactionID() + String.valueOf(minutes) + String.valueOf(seconds));
            }
            wallet.setWalletOrderID(Constant.getInstance().PhonepeQrcode_transactionid);
        }
        wallet.setWalletType(9);
        wallet.setWalletURL("");
        return wallet;
    }


    private ManualDiscCheckReq getManualDiscCheckReq() {
        ManualDiscCheckReq manualDiscCheckReq = new ManualDiscCheckReq();
        manualDiscCheckReq.setAutoDiscount(false);
        manualDiscCheckReq.setClearAllDiscount(false);
        manualDiscCheckReq.setCreditAmount(0);
        manualDiscCheckReq.setEprescriptionDiscountPer(0);
        manualDiscCheckReq.setEprescriptionMaxDicountValue(0);
        manualDiscCheckReq.setHealingCardThresholdAmout(0);
        manualDiscCheckReq.setISDiscountCodeRequired(0);
        manualDiscCheckReq.setIsOTPRequired(0);
        manualDiscCheckReq.setNormalSale(true);
        manualDiscCheckReq.setOPTValidate(false);
        manualDiscCheckReq.setPosSalesTransaction(posTransactionEntity());
        manualDiscCheckReq.setRequestStatus(0);
        manualDiscCheckReq.setRequestType("REQUEST");
        return manualDiscCheckReq;
    }

    private boolean showErrorPharmaDocutor() {
        ArrayList<SalesLineEntity> itemsArrayList = getMvpView().getSelectedProducts();
        for (int i = 0; i < itemsArrayList.size(); i++) {
            SalesLineEntity items = itemsArrayList.get(i);
            if (!items.getIsVoid() && items.getCategoryCode().equalsIgnoreCase("P")) {
                if (getMvpView().getDoctorModule() != null) {
                    return getMvpView().getDoctorModule().getDisplayText().contains("SELF") || getMvpView().getDoctorModule().getDisplayText().contains("OTHERS");
                }
            }
        }
        return false;
    }

    /**
     * invoke to initialize the SDK with the merchant key and the device (card
     * reader) with bank keys
     */
    private void doInitializeEzeTap() {
        /**********************************************
         {
         "demoAppKey": "your demo app key",
         "prodAppKey": "your prod app key",
         "merchantName": "your merchant name",
         "userName": "your user name",
         "currencyCode": "INR",
         "appMode": "DEMO/PROD",
         "captureSignature": "true/false",
         "prepareDevice": "true/false"
         }
         **********************************************/
        JSONObject jsonRequest = new JSONObject();
        try {
            /*jsonRequest.put("demoAppKey", Constant.getInstance().prod_app_key);
            jsonRequest.put("prodAppKey", Constant.getInstance().prod_app_key);
            jsonRequest.put("merchantName", Constant.getInstance().merchant_name);
            jsonRequest.put("userName", Constant.getInstance().user_name);
            jsonRequest.put("currencyCode", Constant.getInstance().currency_code);
            jsonRequest.put("appMode", Constant.getInstance().app_mode);
            jsonRequest.put("captureSignature", Constant.getInstance().capture_signature);
            jsonRequest.put("prepareDevice", Constant.getInstance().prepare_device);*/

            /*jsonRequest.put("demoAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("prodAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("merchantName", "9866666344");
            jsonRequest.put("userName", "9866666344");
            jsonRequest.put("currencyCode", "INR");
            jsonRequest.put("appMode", "DEMO");
            jsonRequest.put("captureSignature", "true");
            jsonRequest.put("prepareDevice", "false");*/

            jsonRequest.put("demoAppKey", "472025ba-496e-46ab-99d5-7bd62fcf706e");
            jsonRequest.put("prodAppKey", "472025ba-496e-46ab-99d5-7bd62fcf706e");
            jsonRequest.put("merchantName", "9910943163");
            jsonRequest.put("userName", "9910943163");
            jsonRequest.put("currencyCode", "INR");
            jsonRequest.put("appMode", "PROD");
            jsonRequest.put("captureSignature", "true");
            jsonRequest.put("prepareDevice", "false");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // }

        EzeAPI.initialize(getMvpView().getContext(), REQUEST_CODE_INITIALIZE, jsonRequest);

    }

    /**
     * Wallet generate OTP Request
     */
    private Response<WalletServiceRes> walletServiceResResponse;

    private void generateWalletOTP(WalletServiceReq walletServiceReq) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<WalletServiceRes> call = api.WALLET_SERVICE_RES_CALL(getDataManager().getStoreId(), getDataManager().getGlobalJson().getStateCode(), walletServiceReq);
            call.enqueue(new Callback<WalletServiceRes>() {
                @Override
                public void onResponse(@NotNull Call<WalletServiceRes> call, @NotNull Response<WalletServiceRes> response) {
                    walletServiceResResponse = response;
                    getMvpView().getWalletResponseData(response);
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                            if (walletPaymentDialog != null) {
                                getMvpView().showMessage(response.body().getReturnMessage());
                                if (response.body().getWalletRequestType() == 3) {
                                    walletServiceReq.setOTPTransactionId(response.body().getOTPTransactionId());
                                    walletPaymentDialog.setGenerateOTPSuccess(response.body().getWalletType());
                                } else if (response.body().getWalletRequestType() == 0) {
                                    walletPaymentDialog.dismiss();
                                    walletServiceReq.setRewardsPoint(response.body().getRewardsPoint());
                                    walletServiceReq.setWalletOrderID(response.body().getWalletOrderID());
                                    walletServiceReq.setWalletRefundId(response.body().getWalletRefundId());
                                    walletServiceReq.setWalletTransactionID(response.body().getWalletTransactionID());
                                    generateTenterLineService(response.body().getWalletAmount(), response.body());
                                } else if (response.body().getWalletRequestType() == 4) {
                                    walletPaymentDialog.dismiss();
                                }

                            } else if (response.body().getWalletRequestType() == 5) {
                                walletServiceReq.setRewardsPoint(response.body().getRewardsPoint());
                                walletServiceReq.setWalletOrderID(response.body().getWalletOrderID());
                                walletServiceReq.setWalletRefundId(response.body().getWalletRefundId());
                                walletServiceReq.setWalletTransactionID(response.body().getWalletTransactionID());
                                generateTenterLineService(response.body().getWalletAmount(), response.body());
                            }
                        } else {
                            if (response.body() != null) {
                                getMvpView().partialPaymentDialog("", response.body().getReturnMessage());
                            } else {
                                getMvpView().showMessage("Please Contact ITD Administrator");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<WalletServiceRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }

    private void doPayment(int paymentType) {
        getMvpView().hideKeyboard();
        if (!showErrorPharmaDocutor()) {
            if (checkTrackingWiseConfing(paymentType)) {
                String corpCode = getMvpView().getCalculatedPosTransactionRes().getCorpCode();
                if (corpCode != null && !corpCode.isEmpty()) {
                    //   System.out.println("payment type--->" + paymentType);
                    switch (paymentType) {
                        case 1:
                            getMvpView().onClickCashPaymentBtn();
                            break;
                        case 2:
                            getMvpView().onClickCardPaymentBtn();
                            break;
                        case 3:
                            getMvpView().onClickOneApolloBtn();
                            break;
                        case 4:
                            getMvpView().onClickWalletPaymentBtn();
                            break;
                        case 5:
                            getPharmacyStaffApiDetails("", "ENQUIRY", getMvpView().orderRemainingAmount());
                            break;
                        case 6:
                            getMvpView().onClickSmsPayBtn();
                            break;
                        case 7:
                            getMvpView().onClickVendorPayBtn();
                            break;
                        case 8:
                            getMvpView().onClickCodPayBtn();
                            break;
                        case 9:
                            getMvpView().onClickHdfcPayBtn();
                            break;
                    }
                } else {
                    showMessagePopup("Please select the corporate");
                }

            }
        } else {
            getMvpView().showDoctorSelectError();
        }

    }

    private boolean checkTrackingWiseConfing(int paymentType) {
        if ((!getMvpView().getCorporateModule().getCode().equalsIgnoreCase("5") && getDataManager().getTrackingWiseConfing() != null && getDataManager().getTrackingWiseConfing().get_TrackingConfigration().size() > 0) || (!getMvpView().getCorporateModule().getCode().equalsIgnoreCase("9995") && getDataManager().getTrackingWiseConfing() != null && getDataManager().getTrackingWiseConfing().get_TrackingConfigration().size() > 0)) {
            for (GetTrackingWiseConfing._TrackingConfigrationEntity entity : getDataManager().getTrackingWiseConfing().get_TrackingConfigration()) {
                if (entity.getCorpCode().equalsIgnoreCase(getMvpView().getCorporateModule().getCode()) && entity.getMandatoryCreditPercentage() > 0) {
                    if (paymentType == 5) {
                        if (TextUtils.isEmpty(getMvpView().getCorporateModule().getPrg_Tracking())) {
                            getMvpView().corpPrgTrackingError();
                            return false;
                        } else {

                            getMvpView().showCreditPayment(calculateCorporatePaymentPercentage(entity.getMandatoryCreditPercentage()), entity);
                        }
                    } else if (tenderLineEntities.getTenderLine().size() == 0)
                        getMvpView().partialPaymentDialog("", "Credit Payment Mandatory!! " + entity.getMandatoryCreditPercentage() + " % Payment Must be in credit");
                    else return true;

                    return false;
                }
            }
        }
        // only pharmacy check getPharmacyStaff api
        if (getMvpView().getCorporateModule().getCode().equalsIgnoreCase("5") || getMvpView().getCorporateModule().getCode().equalsIgnoreCase("9995"))
            return true;
        else if (paymentType == 5) {
            if (TextUtils.isEmpty(getMvpView().getCorporateModule().getPrg_Tracking())) {
                getMvpView().corpPrgTrackingError();
                return false;
            } else {
                getMvpView().showCreditPayment(Double.parseDouble(String.format("%.2f", getMvpView().orderRemainingAmount())), new GetTrackingWiseConfing._TrackingConfigrationEntity());
                return false;
            }
        }
        return true;
    }

    private double calculateCorporatePaymentPercentage(int percentage) {
        return Math.round((getMvpView().orderRemainingAmount() / 100.0f) * percentage);
    }

    private CouponDiscount getCouponDiscountRequest(String couponCode, double categoryAmount) {
        CouponDiscount couponDiscount = new CouponDiscount();
        couponDiscount.setCouponCode(couponCode);
        couponDiscount.setCouponDiscountURLApolloServer("http://10.4.14.4:90//CCOfferService.svc//CORP_COUPONDEATILS");
        couponDiscount.setCouponEnquiryDetailsResult(null);
        couponDiscount.setCouponEnquiryRequest(couponEnquiryRequestEntity(couponCode, categoryAmount));
        couponDiscount.setCreditAmount(0);
        couponDiscount.setEprescriptionDiscountPer(0);
        couponDiscount.setEprescriptionMaxDicountValue(0);
        couponDiscount.setHealingCardThresholdAmout(0);
        couponDiscount.setIsAutoDiscount(false);
        couponDiscount.setIsClearAllDiscount(false);
        couponDiscount.setISDiscountCodeRequired(1);
        couponDiscount.setIsNormalSale(false);
        couponDiscount.setIsOTPRequired(0);
        couponDiscount.setOPTValidate(false);
        couponDiscount.setPosSalesTransaction(getMvpView().getCalculatedPosTransactionRes());
        couponDiscount.setRequestStatus(0);
        couponDiscount.setRequestType("APPLYCOUPONDISCOUNT");

        return couponDiscount;
    }

    private CouponDiscount.CouponEnquiryRequestEntity couponEnquiryRequestEntity(String couponCode, double categoryAmount) {
        CouponDiscount.CouponEnquiryRequestEntity requestEntity = new CouponDiscount.CouponEnquiryRequestEntity();
        requestEntity.setCategoryAmount(String.valueOf(categoryAmount));
        requestEntity.setCorporateCode(getMvpView().getCorporateModule().getCode());
        requestEntity.setCouponCode(couponCode);
        requestEntity.setInvoiceAmount(String.valueOf(getMvpView().orderTotalAmount()));
        requestEntity.setRequestType("ENQUIRY");
        requestEntity.setSiteId(getDataManager().getStoreId());
        if (getMvpView().isOnleneOrder()) {
            requestEntity.setTransactionId(getMvpView().getOnlineTransactionId());
        } else {
            requestEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());
        }
        return requestEntity;
    }

    @Override
    public void getPaymentVoidApiCall(CalculatePosTransactionRes calculatePosTransactionRes, PaymentVoidReq.Wallet wallet, int lineNo) {

//        Gson gson=new Gson();
//        String json=gson.toJson(paymentVoidData(calculatePosTransactionRes,wallet));
//        System.out.println("void data"+json);

        PaymentVoidReq paymentVoidReq = paymentVoidData(calculatePosTransactionRes, wallet);

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<PaymentVoidRes> call = api.PAYMENT_VOID_RES_CALL(lineNo, paymentVoidReq);
            call.enqueue(new Callback<PaymentVoidRes>() {
                @Override
                public void onResponse(@NotNull Call<PaymentVoidRes> call, @NotNull Response<PaymentVoidRes> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        getMvpView().onSuccessPaymentVoidData(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PaymentVoidRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    private PaymentVoidReq paymentVoidData(CalculatePosTransactionRes calculatePosTransactionResData, PaymentVoidReq.Wallet wallet) {
        PaymentVoidReq paymentVoidReq = new PaymentVoidReq();
        paymentVoidReq.setWallet(wallet);
        PaymentVoidReq.POSTransaction posTransaction = new PaymentVoidReq.POSTransaction();
        if (calculatePosTransactionResData != null) {
            posTransaction.setAllowedTenderType("");
            posTransaction.setAmounttoAccount((int) calculatePosTransactionResData.getAmounttoAccount());
            posTransaction.setApprovedID("");
            posTransaction.setAWBNo("");
            posTransaction.setBatchTerminalid(calculatePosTransactionResData.getBatchTerminalid());
            posTransaction.setBillingCity(calculatePosTransactionResData.getBillingCity());
            posTransaction.setBusinessDate(calculatePosTransactionResData.getBusinessDate());
            posTransaction.setCancelReasonCode(calculatePosTransactionResData.getCancelReasonCode());
            posTransaction.setChannel(calculatePosTransactionResData.getChannel());
            posTransaction.setComment(calculatePosTransactionResData.getComment());
            posTransaction.setCorpCode(calculatePosTransactionResData.getCorpCode());
            posTransaction.setCouponCode(calculatePosTransactionResData.getCouponCode());
            posTransaction.setCreatedDateTime("");
            posTransaction.setCreatedonPosTerminal(calculatePosTransactionResData.getCreatedonPosTerminal());
            posTransaction.setCurrency(calculatePosTransactionResData.getCurrency());
            posTransaction.setCurrentSalesLine(0);
            posTransaction.setCustAccount(calculatePosTransactionResData.getCustAccount());
            posTransaction.setCustAddress(calculatePosTransactionResData.getCustAddress());
            posTransaction.setCustDiscamount((int) calculatePosTransactionResData.getCustDiscamount());
            posTransaction.setCustomerID(calculatePosTransactionResData.getCustomerID());
            posTransaction.setCustomerName(calculatePosTransactionResData.getCustomerName());
            posTransaction.setCustomerState(calculatePosTransactionResData.getCustomerState());
            posTransaction.setDataAreaId(calculatePosTransactionResData.getDataAreaId());
            posTransaction.setDeliveryDate(calculatePosTransactionResData.getDeliveryDate());
            posTransaction.setDiscAmount((int) calculatePosTransactionResData.getDiscAmount());
            posTransaction.setDiscountRef("");
            posTransaction.setDOB(calculatePosTransactionResData.getDOB());
            posTransaction.setDoctorCode(calculatePosTransactionResData.getDoctorCode());
            posTransaction.setDoctorName(calculatePosTransactionResData.getDoctorName());
            posTransaction.setDSPCode("");
            posTransaction.setEntryStatus((int) calculatePosTransactionResData.getEntryStatus());
            posTransaction.setExpiryDays(0);
            posTransaction.setGender((int) calculatePosTransactionResData.getGender());
            posTransaction.setGrossAmount(calculatePosTransactionResData.getGrossAmount());
            posTransaction.setIPNO(calculatePosTransactionResData.getIPNO());
            posTransaction.setIPSerialNO(calculatePosTransactionResData.getIPSerialNO());
            posTransaction.setISAdvancePayment(calculatePosTransactionResData.getISAdvancePayment());
            posTransaction.setISBatchModifiedAllowed(calculatePosTransactionResData.getISBatchModifiedAllowed());
            posTransaction.setISHBPStore(false);
            posTransaction.setISHyperDelivered(false);
            posTransaction.setISHyperLocalDelivery(false);
            posTransaction.setManualBill(calculatePosTransactionResData.getIsManualBill());
            posTransaction.setISOMSOrder(calculatePosTransactionResData.getISOMSOrder());
            posTransaction.setISOMSValidate(false);
            posTransaction.setISOnlineOrder(false);
            posTransaction.setISPosted(calculatePosTransactionResData.getISPosted());
            posTransaction.setISPrescibeDiscount(calculatePosTransactionResData.getISPrescibeDiscount());
            posTransaction.setISReserved(calculatePosTransactionResData.getISReserved());
            posTransaction.setReturn(calculatePosTransactionResData.getIsReturn());
            posTransaction.setISReturnAllowed(calculatePosTransactionResData.getISReturnAllowed());
            posTransaction.setStockCheck(calculatePosTransactionResData.getIsStockCheck());
            posTransaction.setVoid(calculatePosTransactionResData.getIsVoid());
            posTransaction.setMobileNO(calculatePosTransactionResData.getMobileNO());
            posTransaction.setNetAmount(calculatePosTransactionResData.getNetAmount());
            posTransaction.setNetAmountInclTax(calculatePosTransactionResData.getNetAmountInclTax());
            posTransaction.setNumberofItemLines((int) calculatePosTransactionResData.getNumberofItemLines());
            posTransaction.setNumberofItems((int) calculatePosTransactionResData.getNumberofItems());
            posTransaction.setOMSCreditAmount(0);
            posTransaction.setOrderPrescriptionURL("");
            posTransaction.setOrderSource(calculatePosTransactionResData.getOrderSource());
            posTransaction.setOrderType(calculatePosTransactionResData.getOrderType());
            posTransaction.setPatientID("");
            posTransaction.setPaymentSource(calculatePosTransactionResData.getPaymentSource());
            posTransaction.setPincode(calculatePosTransactionResData.getPincode());
            posTransaction.setPosEvent((int) calculatePosTransactionResData.getPosEvent());
            posTransaction.setReciptId(calculatePosTransactionResData.getReciptId());
            posTransaction.setREFNO(calculatePosTransactionResData.getREFNO());
            posTransaction.setRegionCode(calculatePosTransactionResData.getRegionCode());
            posTransaction.setRemainingamount(calculatePosTransactionResData.getRemainingamount());
            posTransaction.setReminderDays((int) calculatePosTransactionResData.getReminderDays());
            posTransaction.setRequestStatus((int) calculatePosTransactionResData.getRequestStatus());
            posTransaction.setReturnMessage(calculatePosTransactionResData.getReturnMessage());
            posTransaction.setReturnReceiptId(calculatePosTransactionResData.getReturnReceiptId());
            posTransaction.setReturnStore(calculatePosTransactionResData.getReturnStore());
            posTransaction.setReturnTerminal(calculatePosTransactionResData.getReturnTerminal());
            posTransaction.setTransactionId(calculatePosTransactionResData.getTransactionId());
            posTransaction.setReturnType(calculatePosTransactionResData.getReturnType());
            posTransaction.setRoundedAmount((int) calculatePosTransactionResData.getRoundedAmount());
            posTransaction.setSalesOrigin(calculatePosTransactionResData.getSalesOrigin());
            posTransaction.setSEZ((int) calculatePosTransactionResData.getSEZ());
            posTransaction.setShippingMethod(calculatePosTransactionResData.getShippingMethod());
            posTransaction.setShippingMethodDesc("");
            posTransaction.setStaff(calculatePosTransactionResData.getStaff());
            posTransaction.setState(calculatePosTransactionResData.getState());
            posTransaction.setStore(calculatePosTransactionResData.getStore());
            posTransaction.setStoreName(calculatePosTransactionResData.getStoreName());
            posTransaction.setTerminal(calculatePosTransactionResData.getTerminal());
            posTransaction.setTimewhenTransClosed((int) calculatePosTransactionResData.getTimewhenTransClosed());
            posTransaction.setTotalDiscAmount((int) calculatePosTransactionResData.getTotalDiscAmount());
            posTransaction.setTotalManualDiscountAmount((int) calculatePosTransactionResData.getTotalManualDiscountAmount());
            posTransaction.setTotalManualDiscountPercentage((int) calculatePosTransactionResData.getTotalManualDiscountPercentage());
            posTransaction.setTotalMRP(calculatePosTransactionResData.getTotalMRP());
            posTransaction.setTotalTaxAmount(calculatePosTransactionResData.getTotalTaxAmount());
            posTransaction.setTrackingRef(calculatePosTransactionResData.getTrackingRef());
            posTransaction.setTransactionId(calculatePosTransactionResData.getTransactionId());
            posTransaction.setTransDate(calculatePosTransactionResData.getTransDate());
            posTransaction.setTransType((int) calculatePosTransactionResData.getTransType());
            posTransaction.setType((int) calculatePosTransactionResData.getType());
            posTransaction.setVendorCode("");
            posTransaction.setVendorId(calculatePosTransactionResData.getVendorId());
            paymentVoidReq.setPosTransaction(posTransaction);
        }

        List<PaymentVoidReq.POSTransaction.SalesLine> salesLinelist = new ArrayList<>();

        CalculatePosTransactionRes posTransactionRes = getMvpView().getCalculatedPosTransactionRes();
        ArrayList<SalesLineEntity> salesLineEntities = new ArrayList<>();
        for (SalesLineEntity salesLineEntity : posTransactionRes.getSalesLine()) {
            if (!salesLineEntity.getIsVoid()) {
                salesLineEntities.add(salesLineEntity);
            }
        }
        salesLineEntityData = salesLineEntities;

        if (salesLineEntityData != null) {
            for (int i = 0; i < salesLineEntityData.size(); i++) {
                PaymentVoidReq.POSTransaction.SalesLine salesLine = new PaymentVoidReq.POSTransaction.SalesLine();
                salesLine.setAdditionaltax((int) salesLineEntityData.get(i).getAdditionaltax());
                salesLine.setApplyDiscount(salesLineEntityData.get(i).getApplyDiscount());
                salesLine.setBarcode(salesLineEntityData.get(i).getBarcode());
                salesLine.setBaseAmount((int) salesLineEntityData.get(i).getBaseAmount());
                salesLine.setCategory(salesLineEntityData.get(i).getCategory());
                salesLine.setCategoryCode(salesLineEntityData.get(i).getCategoryCode());
                salesLine.setCategoryReference(salesLineEntityData.get(i).getCategoryReference());
                salesLine.setCESSPerc((int) salesLineEntityData.get(i).getCESSPerc());
                salesLine.setCESSTaxCode(salesLineEntityData.get(i).getCESSTaxCode());
                salesLine.setCGSTPerc((int) salesLineEntityData.get(i).getCGSTPerc());
                salesLine.setCGSTTaxCode(salesLineEntityData.get(i).getCGSTTaxCode());
                salesLine.setComment(salesLineEntityData.get(i).getComment());
                salesLine.setDiscAmount((int) salesLineEntityData.get(i).getDiscAmount());
                salesLine.setDiscId(salesLineEntityData.get(i).getDiscId());
                salesLine.setDiscOfferId(salesLineEntityData.get(i).getDiscOfferId());
                salesLine.setDiscountStructureType((int) salesLineEntityData.get(i).getDiscountStructureType());
                salesLine.setDiscountType(salesLineEntityData.get(i).getDiscountType());
                salesLine.setDiseaseType(salesLineEntityData.get(i).getDiseaseType());
                salesLine.setDPCO(salesLineEntityData.get(i).getDPCO());
                salesLine.setExpiry(salesLineEntityData.get(i).getExpiry());
                salesLine.setHsncode_In(salesLineEntityData.get(i).getHsncode_In());
                salesLine.setIGSTPerc((int) salesLineEntityData.get(i).getIGSTPerc());
                salesLine.setIGSTTaxCode(salesLineEntityData.get(i).getIGSTTaxCode());
                salesLine.setInventBatchId(salesLineEntityData.get(i).getInventBatchId());
                salesLine.setChecked(salesLineEntityData.get(i).getIsChecked());
                salesLine.setGeneric(false);
                salesLine.setISPrescribed((int) salesLineEntityData.get(i).getISPrescribed());
                salesLine.setPriceOverride(salesLineEntityData.get(i).getIsPriceOverride());
                salesLine.setISReserved(salesLineEntityData.get(i).getISReserved());
                salesLine.setISStockAvailable(salesLineEntityData.get(i).getISStockAvailable());
                salesLine.setSubsitute(salesLineEntityData.get(i).getIsSubsitute());
                salesLine.setVoid(salesLineEntityData.get(i).getIsVoid());
                salesLine.setItemId(salesLineEntityData.get(i).getItemId());
                salesLine.setItemName(salesLineEntityData.get(i).getItemName());
                salesLine.setLineDiscPercentage((int) salesLineEntityData.get(i).getLineDiscPercentage());
                salesLine.setLinedscAmount((int) salesLineEntityData.get(i).getLinedscAmount());
                salesLine.setLineManualDiscountAmount((int) salesLineEntityData.get(i).getLineManualDiscountAmount());
                salesLine.setLineManualDiscountPercentage((int) salesLineEntityData.get(i).getLineManualDiscountPercentage());
                salesLine.setLineNo(salesLineEntityData.get(i).getLineNo());
                salesLine.setManufacturerCode(salesLineEntityData.get(i).getManufacturerCode());
                salesLine.setManufacturerName(salesLineEntityData.get(i).getManufacturerName());
                salesLine.setMixMode(salesLineEntityData.get(i).getMixMode());
                salesLine.setMMGroupId(salesLineEntityData.get(i).getMMGroupId());
                salesLine.setModifyBatchId(salesLineEntityData.get(i).getModifyBatchId());
                salesLine.setMRP((int) salesLineEntityData.get(i).getMRP());
                salesLine.setNetAmount(salesLineEntityData.get(i).getNetAmount());
                salesLine.setNetAmountInclTax((int) salesLineEntityData.get(i).getNetAmountInclTax());
                salesLine.setOfferAmount((int) salesLineEntityData.get(i).getOfferAmount());
                salesLine.setOfferDiscountType((int) salesLineEntityData.get(i).getOfferDiscountType());
                salesLine.setOfferDiscountValue((int) salesLineEntityData.get(i).getOfferDiscountValue());
                salesLine.setOfferQty((int) salesLineEntityData.get(i).getOfferQty());
                salesLine.setOfferType((int) salesLineEntityData.get(i).getOfferType());
                salesLine.setOmsLineID((int) salesLineEntityData.get(i).getOmsLineID());
                salesLine.setOmsLineRECID((int) salesLineEntityData.get(i).getOmsLineRECID());
                salesLine.setOrderStatus((int) salesLineEntityData.get(i).getOrderStatus());
                salesLine.setOriginalPrice((int) salesLineEntityData.get(i).getOriginalPrice());
                salesLine.setPeriodicDiscAmount((int) salesLineEntityData.get(i).getPeriodicDiscAmount());
                salesLine.setPhysicalBatchID("");
                salesLine.setPhysicalExpiry("");
                salesLine.setPhysicalMRP(0);
                salesLine.setPreviewText(salesLineEntityData.get(i).getPreviewText());
                //  System.out.println("preview text-->" + salesLineEntityData.get(i).getPreviewText());
                salesLine.setPrice((int) salesLineEntityData.get(i).getPrice());
                salesLine.setProductRecID(salesLineEntityData.get(i).getProductRecID());
                salesLine.setQty(salesLineEntityData.get(i).getQty());
                salesLine.setRemainderDays((int) salesLineEntityData.get(i).getRemainderDays());
                salesLine.setRemainingQty((int) salesLineEntityData.get(i).getRemainingQty());
                salesLine.setRetailCategoryRecID(salesLineEntityData.get(i).getRetailCategoryRecID());
                salesLine.setRetailMainCategoryRecID(salesLineEntityData.get(i).getRetailMainCategoryRecID());
                salesLine.setRetailSubCategoryRecID(salesLineEntityData.get(i).getRetailSubCategoryRecID());
                salesLine.setReturnQty((int) salesLineEntityData.get(i).getReturnQty());
                salesLine.setScheduleCategory(salesLineEntityData.get(i).getScheduleCategory());
                salesLine.setScheduleCategoryCode(salesLineEntityData.get(i).getScheduleCategoryCode());
                salesLine.setSGSTPerc((int) salesLineEntityData.get(i).getSGSTPerc());
                salesLine.setSGSTTaxCode(salesLineEntityData.get(i).getSGSTTaxCode());
                salesLine.setStockQty((int) salesLineEntityData.get(i).getStockQty());
                salesLine.setSubCategory(salesLineEntityData.get(i).getSubCategory());
                salesLine.setSubCategoryCode(salesLineEntityData.get(i).getSubCategoryCode());
                salesLine.setSubClassification(salesLineEntityData.get(i).getSubClassification());
                salesLine.setSubstitudeItemId(salesLineEntityData.get(i).getSubstitudeItemId());
                salesLine.setTax((int) salesLineEntityData.get(i).getTax());
                salesLine.setTaxAmount(salesLineEntityData.get(i).getTaxAmount());
                salesLine.setTotal((int) salesLineEntityData.get(i).getTotal());
                salesLine.setTotalDiscAmount((int) salesLineEntityData.get(i).getTotalDiscAmount());
                salesLine.setTotalDiscPct((int) salesLineEntityData.get(i).getTotalDiscPct());
                salesLine.setTotalRoundedAmount((int) salesLineEntityData.get(i).getTotalRoundedAmount());
                salesLine.setTotalTax((int) salesLineEntityData.get(i).getTotalTax());
                salesLine.setUnit(salesLineEntityData.get(i).getUnit());
                salesLine.setUnitPrice((int) salesLineEntityData.get(i).getUnitPrice());
                salesLine.setUnitQty((int) salesLineEntityData.get(i).getUnitQty());
                salesLine.setVariantId(salesLineEntityData.get(i).getVariantId());
                salesLinelist.add(salesLine);
                paymentVoidReq.getPosTransaction().setSalesLines(salesLinelist);
            }
        }

        List<PaymentVoidReq.POSTransaction.TenderLine> tenderLineslist = new ArrayList<>();
        if (calculatePosTransactionResData.getTenderLine() != null) {
            for (int i = 0; i < calculatePosTransactionResData.getTenderLine().size(); i++) {
                PaymentVoidReq.POSTransaction.TenderLine tenderLine = new PaymentVoidReq.POSTransaction.TenderLine();
                tenderLine.setPreviewText(calculatePosTransactionResData.getTenderLine().get(i).getPreviewText());
                tenderLine.setAmountCur((int) calculatePosTransactionResData.getTenderLine().get(i).getAmountCur());
                tenderLine.setAmountMst((int) calculatePosTransactionResData.getTenderLine().get(i).getAmountMst());
                tenderLine.setAmountTendered((int) calculatePosTransactionResData.getTenderLine().get(i).getAmountTendered());
                tenderLine.setBarCode(calculatePosTransactionResData.getTenderLine().get(i).getBarCode());
                tenderLine.setExchRate((int) calculatePosTransactionResData.getTenderLine().get(i).getExchRate());
                tenderLine.setExchRateMst((int) calculatePosTransactionResData.getTenderLine().get(i).getExchRateMst());
                tenderLine.setLineNo((int) calculatePosTransactionResData.getTenderLine().get(i).getLineNo());
                tenderLine.setMobileNo(calculatePosTransactionResData.getTenderLine().get(i).getMobileNo());
                tenderLine.setRewardsPoint(0);
                tenderLine.setTenderId(calculatePosTransactionResData.getTenderLine().get(i).getTenderId());
                tenderLine.setTenderName(calculatePosTransactionResData.getTenderLine().get(i).getTenderName());
                tenderLine.setTenderType((int) calculatePosTransactionResData.getTenderLine().get(i).getTenderType());
                tenderLine.setVoid(calculatePosTransactionResData.getTenderLine().get(i).getIsVoid());
                tenderLine.setWalletOrderId(calculatePosTransactionResData.getTenderLine().get(i).getWalletOrderId());
                tenderLine.setWalletTransactionID(calculatePosTransactionResData.getTenderLine().get(i).getWalletTransactionID());
                tenderLine.setWalletType((int) calculatePosTransactionResData.getTenderLine().get(i).getWalletType());
                tenderLineslist.add(tenderLine);
                paymentVoidReq.getPosTransaction().setTenderLines(tenderLineslist);
            }
        }
        return paymentVoidReq;
    }

    @Override
    public void getPostOnlineOrderApiCall(EPrescriptionModelClassResponse ePrescriptionModelClassResponse, List<EPrescriptionMedicineResponse> ePrescriptionMedicineResponseList, SaveRetailsTransactionRes saveRetailsTransactionRes, CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            GetPostOnlineOrderApiRequest getPostOnlineOrderApiRequest = new GetPostOnlineOrderApiRequest();
            getPostOnlineOrderApiRequest.setOnlineOfferID(ePrescriptionModelClassResponse.getOnlineOfferID());
            getPostOnlineOrderApiRequest.setISPreEnqiryRequired(ePrescriptionModelClassResponse.getISPreEnqiryRequired());
            getPostOnlineOrderApiRequest.setISAddItem(ePrescriptionModelClassResponse.getISAddItem());
            getPostOnlineOrderApiRequest.setISDecreaseQty(ePrescriptionModelClassResponse.getISDecreaseQty());
            getPostOnlineOrderApiRequest.setISRemoveLine(ePrescriptionModelClassResponse.getISRemoveLine());
            getPostOnlineOrderApiRequest.setIsSTKFulFillment(ePrescriptionModelClassResponse.getIsSTKFulFillment());
            getPostOnlineOrderApiRequest.setCorpCode(ePrescriptionModelClassResponse.getCorpCode());

            GetPostOnlineOrderApiRequest.RequestData requestData = new GetPostOnlineOrderApiRequest.RequestData();
            requestData.setCorpCode(ePrescriptionModelClassResponse.getCorpCode());
            requestData.setTrackingRef(saveRetailsTransactionRes.getTrackingRef());
            requestData.setUrl(getDataManager().getGlobalJson().getOnlineOrderURL());
            requestData.setOrderNo(ePrescriptionModelClassResponse.getPrescriptionNo());
            requestData.setSiteId(ePrescriptionModelClassResponse.getShopId());
            requestData.setVendorOrderno(ePrescriptionModelClassResponse.getAtmNo());
            requestData.setPaymentMode(ePrescriptionModelClassResponse.getName1());
            requestData.setAtmNO(ePrescriptionModelClassResponse.getAtmNo());
            requestData.setDocnum("");
            requestData.setRemarks("");
            requestData.setRequestType("PREBILL");//Need clarify
            requestData.setOtp("");
            requestData.setVendorName(ePrescriptionModelClassResponse.getDoctorConCode());
            requestData.setDoctorName(ePrescriptionModelClassResponse.getDoctorName());

            GetPostOnlineOrderApiRequest.BillDetails billDetails = new GetPostOnlineOrderApiRequest.BillDetails();
            billDetails.setEmpID("");
            billDetails.setBillDateTime(saveRetailsTransactionRes.getTransDate());
            billDetails.setBillNumber(saveRetailsTransactionRes.getReciptId());
            billDetails.setBilledBy(getDataManager().getUserName());
            billDetails.setInvoiceValue(saveRetailsTransactionRes.getTotalMRP());
            billDetails.setCashValue(0.0);
            billDetails.setCreditValue(0);
            billDetails.setCodValue(0);
            requestData.setBillDetails(billDetails);

            List<GetPostOnlineOrderApiRequest.ItemDetail> itemDetailList = new ArrayList<>();
            GetPostOnlineOrderApiRequest.ItemDetail itemDetail = new GetPostOnlineOrderApiRequest.ItemDetail();
            for (SaveRetailsTransactionRes.SalesLineEntity salesLineEntity : saveRetailsTransactionRes.getSalesLine()) {
                itemDetail.setPosItemId(salesLineEntity.getItemId());
                itemDetail.setItemId(salesLineEntity.getItemId());
                itemDetail.setItemName(salesLineEntity.getItemName());
                itemDetail.setBatchId(salesLineEntity.getInventBatchId());
                itemDetail.setReqQty(salesLineEntity.getQty());
                itemDetail.setIssuedQty(salesLineEntity.getQty());// Need clarify
                itemDetail.setMrp(salesLineEntity.getMRP());
                itemDetail.setDiscountMRP(salesLineEntity.getDiscAmount());
                itemDetail.setLineAmount((int) (salesLineEntity.getQty() * salesLineEntity.getMRP()));
                itemDetail.setLineAmountWithDiscount(salesLineEntity.getLinedscAmount());
                itemDetail.setStatus(false);// Need clarify
                itemDetail.setIsSubstitute(false);
                List<GetPostOnlineOrderApiRequest.Substitute> substituteList = new ArrayList<>();
                GetPostOnlineOrderApiRequest.Substitute substitute = new GetPostOnlineOrderApiRequest.Substitute();
                for (SalesLineEntity salesLine : customerDataResBean.getSalesLine()) {
                    if (salesLine.getItemId().equalsIgnoreCase(salesLineEntity.getItemId())) {
                        itemDetail.setIsSubstitute(true);
                        substitute.setSubstituteItemID(salesLine.getSubstitudeItemId());
                        substitute.setSubstituteItemName("");
                        substitute.setSubstituteBatchId("");
                        substitute.setIssuedQty(0);
                        substituteList.add(substitute);
                        break;
                    }
                }


                itemDetail.setSubstitute(substituteList);
                itemDetailList.add(itemDetail);

            }

            requestData.setItemDetails(itemDetailList);

            getPostOnlineOrderApiRequest.setRequestData(requestData);

            Call<GetPostOnlineOrderApiResponse> call = api.GET_POST_ONLINE_ORDER_API_CALL(getPostOnlineOrderApiRequest);

            call.enqueue(new Callback<GetPostOnlineOrderApiResponse>() {
                @Override
                public void onResponse(@NotNull Call<GetPostOnlineOrderApiResponse> call, @NotNull Response<GetPostOnlineOrderApiResponse> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().onSuccessGetPostOnlineOrderApi(response.body());
                        else getMvpView().onFailedGetPostOnlineOrderApi(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetPostOnlineOrderApiResponse> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public GetTenderTypeRes.GetTenderTypeResultEntity getTenderTypeResultEntity() {
        return getDataManager().getTenderTypeResultEntity();
    }

    @Override
    public void getOnlineOrderCorporateList(GetOnlineCorporateListApiRequest request, double amount) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<GetOnlineCorporateListApiResponse> call = api.GET_ONLINE_CORPORATELIST(request);
            call.enqueue(new Callback<GetOnlineCorporateListApiResponse>() {
                @Override
                public void onResponse(@NotNull Call<GetOnlineCorporateListApiResponse> call, @NotNull Response<GetOnlineCorporateListApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        getMvpView().SuccessOnlineorderCorporatelist(response.body(), amount);
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetOnlineCorporateListApiResponse> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public boolean isMposV1Flow() {
        return getDataManager().isV1Flow();
    }
}