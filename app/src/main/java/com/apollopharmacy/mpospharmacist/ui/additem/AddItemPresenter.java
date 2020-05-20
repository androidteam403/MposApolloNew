package com.apollopharmacy.mpospharmacist.ui.additem;

import android.text.TextUtils;
import android.view.View;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CouponDiscount;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OTPRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PharmacyStaffAPIReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.TenderLineEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.TypeEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsReqModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.Wallet;
import com.apollopharmacy.mpospharmacist.ui.additem.model.WalletServiceReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.eze.api.EzeAPI;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemPresenter<V extends AddItemMvpView> extends BasePresenter<V>
        implements AddItemMvpPresenter<V> {
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

    @Override
    public void onClickClearAllBtn() {
        if (tenderLineEntities.getTenderLine().size() > 0) {
            getMvpView().partialPaymentDialog("Alert!","Partial Payment done,Kindly void payment lines");
        } else {
            getMvpView().onClearAll();
        }
    }

    @Override
    public void onPayButtonClick() {
        getMvpView().hideKeyboard();
        getMvpView().onPayButtonClick();
    }

    @Override
    public void onClickCardPayment() {
        doPayment(2);
    }

    @Override
    public void onClickCashPayment() {
        doPayment(1);
    }

    @Override
    public void onClickOneApolloPayment() {
        doPayment(3);
    }

    @Override
    public void onClickWalletPayment() {
        doPayment(4);
    }

    @Override
    public void onClickCreditPayment() {
        doPayment(5);
    }

    @Override
    public void onClickCardPaymentPay() {
        if (TextUtils.isEmpty(getMvpView().getCardPaymentAmount())) {
            getMvpView().setErrorCardPaymentAmountEditText("Enter Amount");
        } else if (getMvpView().orderRemainingAmount() < Double.parseDouble(getMvpView().getCardPaymentAmount())) {
            getMvpView().setErrorCardPaymentAmountEditText("Entered Amount greater then Order amount");
        } else {
            if(getDataManager().getGlobalJson().isISCardBilling() && getDataManager().getGlobalJson().isISEzetapActive())
                doInitializeEzeTap();
            else if(getDataManager().getGlobalJson().isISCardBilling() && !getDataManager().getGlobalJson().isISEzetapActive())
                generateTenterLineService(Double.parseDouble(getMvpView().getCardPaymentAmount()),null);
        }
    }

    @Override
    public void onClickCashPaymentPay() {
        if (!showErrorPharmaDocutor()) {
            if (TextUtils.isEmpty(getMvpView().getCashPaymentAmount())) {
                getMvpView().setErrorCashPaymentAmountEditText("Enter Amount");
            } else {
                generateTenterLineService(Double.parseDouble(getMvpView().getCashPaymentAmount()),null);
            }
        } else {
            getMvpView().showDoctorSelectError();
        }


    }

    @Override
    public void onClickCreditPaymentPay() {
        if (!showErrorPharmaDocutor()) {
            getPharmacyStaffApiDetails("ENQUIRY",getMvpView().orderRemainingAmount());
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
        if (getMvpView().orderRemainingAmount() <= Double.parseDouble(getMvpView().getOneApolloPoints())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService();
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
    public void onClickEditItemsList() {
        getMvpView().onClickEditItemsList();
    }

    @Override
    public void calculatePosTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<CalculatePosTransactionRes> call = api.CALCULATE_POS_TRANSACTION_RES_CALL(posTransactionEntity());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
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
    public boolean validateOneApolloPoints() {
        double enterPoints = Double.parseDouble(getMvpView().getOneApolloPoints());
        double availablePoints = Double.parseDouble(getMvpView().onApolloPointsAvailablePoints().getAvailablePoints());
        return enterPoints <= availablePoints;
    }

    @Override
    public void onClickOTPVerify() {
        if (!TextUtils.isEmpty(getMvpView().getOneApolloOtp())) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService();
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
            ApiInterface api = ApiClient.getApiService();
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
        generateTenterLineService(amount,null);
    }

    @Override
    public void onClickGenerateBill() {
        saveRetailTransaction();
    }

    // private GetTenderTypeRes.GetTenderTypeResultEntity tenderTypeResultEntity;
    @Override
    public void getTenderTypeApi() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();

            Call<GetTenderTypeRes> call = api.GET_TENDER_TYPE_RES_CALL(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<GetTenderTypeRes>() {
                @Override
                public void onResponse(@NotNull Call<GetTenderTypeRes> call, @NotNull Response<GetTenderTypeRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getGetTenderTypeResult() != null && response.body().getGetTenderTypeResult().getRequestStatus() == 0) {
                            //                    tenderTypeResultEntity = response.body().getGetTenderTypeResult();
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getGetTenderTypeResult().getReturnMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetTenderTypeRes> call, @NotNull Throwable t) {
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
    public void onClickManualDisc() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();

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
        for(GetTrackingWiseConfing._TrackingConfigrationEntity entity : getDataManager().getTrackingWiseConfing().get_TrackingConfigration()){
            if(entity.getCorpCode().equalsIgnoreCase(getMvpView().getCorporateModule().getCode()) && entity.getISCouponBilling() == 1){
                if(!TextUtils.isEmpty(entity.getCategory())){
                    if (entity.getCategory().equalsIgnoreCase("PHARMA"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getPharmaTotalAmount();
                    else if (entity.getCategory().equalsIgnoreCase("FMCG"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getFmcgTotalAmount();
                    else if (entity.getCategory().equalsIgnoreCase("PL"))
                        categoryAmount = getMvpView().getOrderPriceInfoModel().getPlTotalAmount();
                }else{
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
            ApiInterface api = ApiClient.getApiService();

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
            ApiInterface api = ApiClient.getApiService();
            String url = getDataManager().getGlobalJson().getSMSAPI();
            String otp = String.valueOf(CommonUtils.generatorOTP(8));
            String message = "Dear Customer, Your Smart Saver Offer OTP is " + otp + ",Use the OTP to avail the Offer";
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
    public void validateOTP() {

    }

    @Override
    public void validateOneApolloPoints(String userMobileNumber, String transactionID) {
        if (getMvpView().isNetworkConnected()) {
            //  getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
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
    public void onClickRedeemPoints() {

    }

    private CalculatePosTransactionRes tenderLineEntities = new CalculatePosTransactionRes();

    @Override
    public void generateTenterLineService(double amount, WalletServiceRes walletServiceRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();

            Call<GenerateTenderLineRes> call = api.GENERATE_TENDER_LINE_RES_CALL(amount, generateTenderLineReq(amount,walletServiceRes));
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

    @Override
    public void clearAllVoidTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<CalculatePosTransactionRes> call = api.VOID_TRANSACTION(getMvpView().getCalculatedPosTransactionRes());
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
            ApiInterface api = ApiClient.getApiService();
            Call<CalculatePosTransactionRes> call = api.VOID_TRANSACTION(getMvpView().getCalculatedPosTransactionRes());
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
            ApiInterface api = ApiClient.getApiService();
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
    public void checkAllowedPaymentMode(PaymentMethodModel paymentMethodModel) {
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(false);
        paymentMethodModel.setOneApolloMode(false);
        paymentMethodModel.setWalletMode(false);
        paymentMethodModel.setCreditMode(false);
        if(getDataManager().getAllowedPaymentModeRes() != null && getDataManager().getAllowedPaymentModeRes().get_PaymentMethodList().size() > 0) {
            for (AllowedPaymentModeRes._PaymentMethodListEntity entity : getDataManager().getAllowedPaymentModeRes().get_PaymentMethodList()) {
                if (getMvpView().getCorporateModule().getPayMode().equals(entity.getPaymentMode())) {
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
                        paymentMethodModel.setEnableCardBtn(true);
                    }

                    //Gift Tender
                    if (!entity.getCombinationCode().contains("6")) {
                        paymentMethodModel.setEnableApolloBtn(false);
                    } else {
                        paymentMethodModel.setEnableApolloBtn(true);
                    }

                    //Vendor Payment Tender
//            if (!entity.getCombinationCode().contains("7"))
//            { btnVendorPayment.Enabled = false; }
//            else
//            { btnVendorPayment.Enabled = true; }

                    //Wallets Tender
                    if (!entity.getCombinationCode().contains("5")) {
                        paymentMethodModel.setEnableWalletBtn(false);
                    } else {
                        paymentMethodModel.setEnableWalletBtn(true);
                    }

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
                        paymentMethodModel.setEnableApolloBtn(false);
                        paymentMethodModel.setEnableWalletBtn(false);
                        paymentMethodModel.setEnableCreaditBtn(false);
                    }
                }

            }
        }
    }

    @Override
    public void onSelectPhonePe() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        for(GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()){
            if(tenderTypeEntity.getTender().equalsIgnoreCase("PhonePe")){
                walletServiceReq.setWalletType(4);
                walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
            }
        }
        showWalletPaymentDialog("PhonePe Transaction", true,walletServiceReq);
    }

    @Override
    public void onSelectPayTm() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        for(GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()){
            if(tenderTypeEntity.getTender().equalsIgnoreCase("PAYTM")){
                walletServiceReq.setWalletType(3);
                walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
            }
        }
        showWalletPaymentDialog("Paytm Transaction", false,walletServiceReq);
    }

    @Override
    public void onSelectAirtelMoney() {
        WalletServiceReq walletServiceReq = new WalletServiceReq();
        walletServiceReq.setOTP("");
        walletServiceReq.setOTPTransactionId("");
        walletServiceReq.setPOSTerminal(getDataManager().getTerminalId());
        walletServiceReq.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
        walletServiceReq.setRequestStatus(0);
        walletServiceReq.setRequestURL("");
        walletServiceReq.setResponse("");
        walletServiceReq.setReturnMessage("");
        walletServiceReq.setRewardsPoint(0);
        walletServiceReq.setWalletOrderID("");
        walletServiceReq.setWalletRefundId("");
        walletServiceReq.setWalletTransactionID("");

        for(GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()){
            if(tenderTypeEntity.getTender().equalsIgnoreCase("Airtel")){
                walletServiceReq.setWalletType(2);
                walletServiceReq.setWalletURL(tenderTypeEntity.getTenderURL());
            }
        }
        showWalletPaymentDialog("Airtel Money Transaction",true,walletServiceReq);
    }

    @Override
    public boolean validTenderLimit(double amount, String tenderName) {
        for(GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()) {
            if (tenderTypeEntity.getTender().equalsIgnoreCase(tenderName)) {
                if(tenderTypeEntity.getTenderLimit() < amount){
                    getMvpView().partialPaymentDialog("","Allowed Tender Limit is "+ tenderTypeEntity.getTenderLimit() +"!");
                    return false;
                }else{
                    return  true;
                }
            }
        }
        return true;
    }

    @Override
    public void getPharmacyStaffApiDetails(String action,double amount) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            PharmacyStaffAPIReq staffAPIReq = new PharmacyStaffAPIReq();
            staffAPIReq.setAction(action);
            staffAPIReq.setAmount(amount);
            staffAPIReq.setDocNum(getMvpView().getDoctorModule().getCode());

            if (TextUtils.isEmpty(getMvpView().getCorporateModule().getPrg_Tracking())) {
                getMvpView().corpPrgTrackingError();
                getMvpView().hideLoading();
                return;
            } else
                staffAPIReq.setEmpId(getMvpView().getCorporateModule().getPrg_Tracking());

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
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if(action.equalsIgnoreCase("ENQUIRY")) {
                                double availableAmount = Double.parseDouble(response.body().getTotalBalance()) - Double.parseDouble(response.body().getUsedBalance());
                                if (amount <= availableAmount) {
                                    getPharmacyStaffApiDetails("GENOTP",amount);
                                } else {
                                    getMvpView().partialPaymentDialog("", "Balance Not Available");
                                }
                            }else if(action.equalsIgnoreCase("GENOTP")){
                                if(response.body().getValidateOTP().equalsIgnoreCase("true")){
                                        getMvpView().showOTPPopUp(response.body().getOTP());
                                }else{
                                    getMvpView().getPaymentMethod().setCreditMode(true);
                                    generateTenterLineService(amount,null);
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
    public void applyCouponCodeApi(String couponCode,double categoryAmount) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<CouponDiscount> call = api.COUPON_DISCOUNT_CALL(getCouponDiscountRequest(couponCode,categoryAmount));
            call.enqueue(new Callback<CouponDiscount>() {
                @Override
                public void onResponse(@NotNull Call<CouponDiscount> call, @NotNull Response<CouponDiscount> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if(response.body().getCouponEnquiryDetailsResult() != null &&
                                    response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult() != null &&
                                        response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult().getRequestStatus()){

                            }else{
                                getMvpView().partialPaymentDialog("",response.body().getCouponEnquiryDetailsResult().getCoupenDetailsResult().getRequestMessage());
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

    WalletPaymentDialog walletPaymentDialog;
    private void showWalletPaymentDialog(String title,boolean isEnableGenerateOtp,WalletServiceReq walletServiceReq){
         walletPaymentDialog = new WalletPaymentDialog(getMvpView().getContext());
        walletPaymentDialog.setTitle(title);
        walletPaymentDialog.setEnableGenerateOTP(isEnableGenerateOtp);
        walletPaymentDialog.setCalculatedPosTransaction(getMvpView().getCalculatedPosTransactionRes());
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
                if(walletPaymentDialog.isValidateAmount(walletServiceReq)){
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
                if(walletPaymentDialog.isValidateOTP(walletServiceReq)){
                   // walletPaymentDialog.dismiss();
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

    private void saveRetailTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            CalculatePosTransactionRes posTransactionRes = getMvpView().getCalculatedPosTransactionRes();
            ArrayList<SalesLineEntity> salesLineEntities = new ArrayList<>();
            for(SalesLineEntity salesLineEntity : posTransactionRes.getSalesLine()){
                if(!salesLineEntity.getIsVoid()){
                    salesLineEntities.add(salesLineEntity);
                }
            }
            posTransactionRes.setSalesLine(salesLineEntities);
            Call<SaveRetailsTransactionRes> call = api.SAVE_RETAILS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<SaveRetailsTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<SaveRetailsTransactionRes> call, @NotNull Response<SaveRetailsTransactionRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().onSuccessSaveRetailTransaction(response.body());
                        else
                            getMvpView().onFailedSaveRetailsTransaction(response.body());
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
        tenderLineReq.setWallet(wallet(amount,walletServiceRes));
        return tenderLineReq;
    }

    @Override
    public CalculatePosTransactionRes getTenderLineEntities() {
        return tenderLineEntities;
    }

    private TypeEntity typeEntity() {
        TypeEntity typeEntity = new TypeEntity();
        if (Singletone.getInstance().tenderTypeResultEntity != null && Singletone.getInstance().tenderTypeResultEntity.get_TenderType().size() > 0) {
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()) {
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
                }
            }
        }

        return typeEntity;
    }

    private POSTransactionEntity posTransactionEntity() {
        POSTransactionEntity posTransactionEntity = new POSTransactionEntity();
        posTransactionEntity.setAmounttoAccount(0);
        posTransactionEntity.setBatchTerminalid("");
        posTransactionEntity.setBillingCity("");
        posTransactionEntity.setBusinessDate(CommonUtils.getCurrentDate(CommonUtils.DATE_FORMAT_DD_MMM_YYYY));
        posTransactionEntity.setCancelReasonCode("");
        posTransactionEntity.setChannel(getDataManager().getGlobalJson().getChannel());
        posTransactionEntity.setComment("");
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
        posTransactionEntity.setISOMSOrder(false);
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
        posTransactionEntity.setSalesOrigin("");
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
        posTransactionEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());// Trancaction id
        posTransactionEntity.setTransDate(CommonUtils.getCurrentDate("dd-MMM-YYYY hh:mm:ss"));// payment date time format `18-Feb-2020 03:01:20`
        posTransactionEntity.setTransType(0);
        posTransactionEntity.setType(2);
        posTransactionEntity.setVendorId("");
        return posTransactionEntity;
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
        if(walletServiceRes != null){
            wallet.setMobileNo(walletServiceRes.getMobileNo());
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
        }else{
            wallet.setMobileNo(getMvpView().getCustomerModule().getMobileNo());
            wallet.setOTP(getMvpView().getOneApolloOtp());
            wallet.setOTPTransactionId("");
            wallet.setPOSTerminal(getDataManager().getTerminalId());
            wallet.setPOSTransactionID(getMvpView().getTransactionModule().getTransactionID());
            wallet.setRequestStatus(0);
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setReturnMessage("");
            wallet.setRewardsPoint(0);
            wallet.setWalletAmount(amount);
            wallet.setWalletOrderID("");
            wallet.setWalletRefundId("");
            wallet.setWalletRequestType(0);
        }

        if (getMvpView().getValidateOneApolloPoints() != null && getMvpView().getValidateOneApolloPoints().getRRNO() != null)
            wallet.setWalletTransactionID(getMvpView().getValidateOneApolloPoints().getRRNO());
        else
            wallet.setWalletTransactionID("");
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
            if (items.getCategoryCode().equalsIgnoreCase("P")) {
                if (getMvpView().getDoctorModule() != null) {
                    return getMvpView().getDoctorModule().getDisplayText().contains("SELF")
                            || getMvpView().getDoctorModule().getDisplayText().contains("OTHERS");
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
//        if(Setting.config != null) {
//            try {
//                jsonRequest.put("demoAppKey", Setting.config.getAppKey());
//                jsonRequest.put("prodAppKey", Setting.config.getAppKey());
//                jsonRequest.put("merchantName", Setting.config.getMerchantName());
//                jsonRequest.put("userName", Setting.config.getUsername());
//                jsonRequest.put("currencyCode", "INR");
//                jsonRequest.put("appMode", Setting.config.getAppMode());
//                jsonRequest.put("captureSignature", "true");
//                jsonRequest.put("prepareDevice", "false");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        try {
            jsonRequest.put("demoAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("prodAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("merchantName", "9866666344");
            jsonRequest.put("userName", "9866666344");
            jsonRequest.put("currencyCode", "INR");
            jsonRequest.put("appMode", "DEMO");
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
    private void generateWalletOTP(WalletServiceReq walletServiceReq) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<WalletServiceRes> call = api.WALLET_SERVICE_RES_CALL(getDataManager().getStoreId(),getDataManager().getGlobalJson().getStateCode(),walletServiceReq);
            call.enqueue(new Callback<WalletServiceRes>() {
                @Override
                public void onResponse(@NotNull Call<WalletServiceRes> call, @NotNull Response<WalletServiceRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0){
                            if(walletPaymentDialog != null){
                                getMvpView().showMessage(response.body().getReturnMessage());
                                if(response.body().getWalletRequestType() == 3){
                                    walletServiceReq.setOTPTransactionId(response.body().getOTPTransactionId());
                                    walletPaymentDialog.setGenerateOTPSuccess(response.body().getWalletType());
                                }else if(response.body().getWalletRequestType() == 0){
                                    walletPaymentDialog.dismiss();
                                    generateTenterLineService(response.body().getWalletAmount(),response.body());
                                }else if(response.body().getWalletRequestType() == 4){
                                    walletPaymentDialog.dismiss();
                                }
                            }
                        }else{
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getReturnMessage());
                            }else{
                                getMvpView().showMessage("Something went wrong please try again");
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

    private void doPayment(int paymentType){
        getMvpView().hideKeyboard();
        if(!showErrorPharmaDocutor()){
            if(checkTrackingWiseConfing(paymentType)) {
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
                        getPharmacyStaffApiDetails("ENQUIRY",getMvpView().orderRemainingAmount());
                        break;
                }
            }
        }else{
            getMvpView().showDoctorSelectError();
        }

    }
    private boolean checkTrackingWiseConfing(int paymentType){
        if(!getMvpView().getCorporateModule().getCode().equalsIgnoreCase("5") && tenderLineEntities.getTenderLine().size() == 0 && getDataManager().getTrackingWiseConfing() != null && getDataManager().getTrackingWiseConfing().get_TrackingConfigration().size() > 0){
            for(GetTrackingWiseConfing._TrackingConfigrationEntity entity : getDataManager().getTrackingWiseConfing().get_TrackingConfigration()){
                if(entity.getCorpCode().equalsIgnoreCase(getMvpView().getCorporateModule().getCode()) && entity.getMandatoryCreditPercentage() > 0){
                    if(paymentType == 5)
                        getMvpView().showCreditPayment(calculateCorporatePaymentPercentage(entity.getMandatoryCreditPercentage()),entity);
                    else
                        getMvpView().partialPaymentDialog("","Credit Payment Mandatory!! "+ entity.getMandatoryCreditPercentage() +" % Payment Must be in credit");

                    return false;
                }
            }
        }
        return true;
    }

    private double calculateCorporatePaymentPercentage(int percentage){
        return  Math.round((getMvpView().orderRemainingAmount() / 100.0f) * percentage);
    }

    private CouponDiscount getCouponDiscountRequest(String couponCode,double categoryAmount){
        CouponDiscount couponDiscount = new CouponDiscount();
        couponDiscount.setCouponCode(couponCode);
        couponDiscount.setCouponDiscountURLApolloServer("http://10.4.14.4:90//CCOfferService.svc//CORP_COUPONDEATILS");
        couponDiscount.setCouponEnquiryDetailsResult(null);
        couponDiscount.setCouponEnquiryRequest(couponEnquiryRequestEntity(couponCode,categoryAmount));
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

    private CouponDiscount.CouponEnquiryRequestEntity couponEnquiryRequestEntity(String couponCode,double categoryAmount){
        CouponDiscount.CouponEnquiryRequestEntity requestEntity = new CouponDiscount.CouponEnquiryRequestEntity();
        requestEntity.setCategoryAmount(String.valueOf(categoryAmount));
        requestEntity.setCorporateCode(getMvpView().getCorporateModule().getCode());
        requestEntity.setCouponCode(couponCode);
        requestEntity.setInvoiceAmount(String.valueOf(getMvpView().orderTotalAmount()));
        requestEntity.setRequestType("ENQUIRY");
        requestEntity.setSiteId(getDataManager().getStoreId());
        requestEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());
        return requestEntity;
    }
}
