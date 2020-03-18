package com.apollopharmacy.mpospharmacist.ui.additem;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsReqModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;
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
        getMvpView().onClearAll();
    }

    @Override
    public void onPayButtonClick() {
        getMvpView().onPayButtonClick();
    }

    @Override
    public void onClickCardPayment() {
        getMvpView().onClickCardPaymentBtn();
    }

    @Override
    public void onClickCashPayment() {
        getMvpView().onClickCashPaymentBtn();
    }

    @Override
    public void onClickOneApolloPayment() {
        getMvpView().onClickOneApolloBtn();
    }

    @Override
    public void onClickCardPaymentPay() {
        if (TextUtils.isEmpty(getMvpView().getCardPaymentAmount())) {
            getMvpView().setErrorCardPaymentAmountEditText("Enter Amount");
        } else {
            doInitializeEzeTap();
        }
    }

    @Override
    public void onClickCashPaymentPay() {
        if (TextUtils.isEmpty(getMvpView().getCashPaymentAmount())) {
            getMvpView().setErrorCashPaymentAmountEditText("Enter Amount");
        } else {
            generateTenterLineService();
        }
    }

    @Override
    public void onClickOneApolloPaymentPay() {
        if(!TextUtils.isEmpty(getMvpView().getOneApolloPoints()) ) {
            if(validateOneApolloPoints()) {
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
                    requestDataEntity.setPoints(getMvpView().getOneApolloPoints());
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
                                }else {
                                    getMvpView().hideLoading();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                        }
                    });
                } else {
                    getMvpView().onError("Internet Connection Not Available");
                }
            }else{
                getMvpView().setErrorOneApolloPointsEditText("Enter valid Points");
            }
        }else{
            getMvpView().setErrorOneApolloPointsEditText("Enter Points");
        }
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
                        getMvpView().onSuccessCalculatePosTransaction(response.body());

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public boolean validateOneApolloPoints() {
        double enterPoints = Double.parseDouble(getMvpView().getOneApolloPoints());
        double availablePoints = Double.parseDouble(getMvpView().getValidateOneApolloPoints().getAvailablePoints());
        return enterPoints <= availablePoints;
    }

    @Override
    public void onClickOTPVerify() {
        if(!TextUtils.isEmpty(getMvpView().getOneApolloOtp())){
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
                                getMvpView().onSuccessOneApolloOtp(response.body().getOneApolloProcessResult());
                                //generateTenterLineService();

                            }else {
                                getMvpView().hideLoading();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }else{
            getMvpView().setErrorOneApolloOtpEditText("Enter Otp");
        }
    }

    @Override
    public void onClickReSendOTP() {
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

                        }else {
                            getMvpView().hideLoading();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ValidatePointsResModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onSuccessCardPayment(String response) {
        getMvpView().updatePayedAmount(Double.parseDouble(getMvpView().getCardPaymentAmount()),2);
        //generateTenterLineService();
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

            Call<GetTenderTypeRes> call = api.GET_TENDER_TYPE_RES_CALL(getDataManager().getStoreId(),getDataManager().getDataAreaId(),new Object());
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
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ManualDiscCheckRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
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
                            getMvpView().onSuccessCalculatePosTransaction(response.body().getPosSalesTransaction());
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ManualDiscCheckRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void validateOneApolloPoints(String userMobileNumber, String transactionID) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
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
                        if (response.body().getRequestStatus() == 0) {
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
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickRedeemPoints() {

    }
private ArrayList<GenerateTenderLineRes.TenderLineEntity> tenderLineEntities = new ArrayList<>();
    private void generateTenterLineService(){
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();

            Call<GenerateTenderLineRes> call = api.GENERATE_TENDER_LINE_RES_CALL(getMvpView().getCashPaymentAmount(),generateTenderLineReq());
            call.enqueue(new Callback<GenerateTenderLineRes>() {
                @Override
                public void onResponse(@NotNull Call<GenerateTenderLineRes> call, @NotNull Response<GenerateTenderLineRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        //Dismiss Dialog
                        if (response.body() != null) {
                            tenderLineEntities.addAll(response.body().getGenerateTenderLineResult().getTenderLine());
                            getMvpView().updatePayedAmount(response.body().getGenerateTenderLineResult().getGrossAmount(), 1);
                            //  saveRetailTransaction(response.body());
                        }else {
                            getMvpView().onFailedGenerateTenderLine(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GenerateTenderLineRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    private void saveRetailTransaction() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();

            Call<SaveRetailsTransactionRes> call = api.SAVE_RETAILS_TRANSACTION_RES_CALL(getSaveTransactionReq());
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
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    // Generate TenderLine Request Formation
    private GenerateTenderLineReq generateTenderLineReq() {
        GenerateTenderLineReq tenderLineReq = new GenerateTenderLineReq();
        tenderLineReq.setType(typeEntity());
        tenderLineReq.setPOSTransaction(posTransactionEntity());
        if(getMvpView().getPaymentMethod().isOneApolloMode()){
            tenderLineReq.setWallet(wallet());
        }
        return tenderLineReq;
    }
    private GenerateTenderLineReq.POSTransactionEntity getSaveTransactionReq(){
        return posTransactionEntity();
    }
    private GenerateTenderLineReq.TypeEntity typeEntity() {
        GenerateTenderLineReq.TypeEntity typeEntity = new GenerateTenderLineReq.TypeEntity();
        if(Singletone.getInstance().tenderTypeResultEntity != null && Singletone.getInstance().tenderTypeResultEntity.get_TenderType().size() > 0){
            for(GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()){
                if(getMvpView().getPaymentMethod().isCashMode()) {
                    if(tenderTypeEntity.getTender().equalsIgnoreCase("Cash")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                }else if(getMvpView().getPaymentMethod().isCardMode()) {
                    if(tenderTypeEntity.getTender().equalsIgnoreCase("card")) {
                        typeEntity.setTender(tenderTypeEntity.getTender());
                        typeEntity.setTenderCombinationType(tenderTypeEntity.getTenderCombinationType());
                        typeEntity.setTenderLimit(tenderTypeEntity.getTenderLimit());
                        typeEntity.setTenderTypeId(tenderTypeEntity.getTenderTypeId());
                        typeEntity.setPosOpereration(tenderTypeEntity.getPosOpereration());
                        typeEntity.setRoundingMethod(tenderTypeEntity.getRoundingMethod());
                        typeEntity.setTenderURL(tenderTypeEntity.getTenderURL());
                    }
                }else if(getMvpView().getPaymentMethod().isOneApolloMode()) {
                    if(tenderTypeEntity.getTender().equalsIgnoreCase("gift")) {
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

    private GenerateTenderLineReq.POSTransactionEntity posTransactionEntity() {
        GenerateTenderLineReq.POSTransactionEntity posTransactionEntity = new GenerateTenderLineReq.POSTransactionEntity();
        posTransactionEntity.setAmounttoAccount(0);
        posTransactionEntity.setBatchTerminalid("");
        posTransactionEntity.setBillingCity("");
        posTransactionEntity.setBusinessDate(CommonUtils.getCurrentDate(CommonUtils.DATE_FORMAT_DD_MMM_YYYY));
        posTransactionEntity.setCancelReasonCode("");
        posTransactionEntity.setChannel(getDataManager().getGlobalJson().getChannel());
        posTransactionEntity.setComment("");
        posTransactionEntity.setCorpCode(getMvpView().getCorporateModule().getCode());
        posTransactionEntity.setCouponCode("");
        posTransactionEntity.setCreatedonPosTerminal(getMvpView().getTransactionModule().getTerminalID());// Terminal Id
        posTransactionEntity.setCurrency(getDataManager().getGlobalJson().getCurrency());
        posTransactionEntity.setCurrentSalesLine(0);
        posTransactionEntity.setCustAccount("");
        posTransactionEntity.setCustAddress("");
        posTransactionEntity.setCustDiscamount(0);
        posTransactionEntity.setCustomerID(getMvpView().getCustomerModule().getCustId());// customer Id
        posTransactionEntity.setCustomerName(getMvpView().getCustomerModule().getCardName());
        posTransactionEntity.setCustomerState("");
        posTransactionEntity.setDataAreaId(getMvpView().getTransactionModule().getDataAreaID()); // DataAreId
        posTransactionEntity.setDeliveryDate("");
        posTransactionEntity.setDiscAmount(0);
        posTransactionEntity.setDOB("");
        posTransactionEntity.setDoctorCode(getMvpView().getDoctorModule()!= null ? getMvpView().getDoctorModule().getCode() : "");
        posTransactionEntity.setDoctorName(getMvpView().getDoctorModule()!= null ? getMvpView().getDoctorModule().getDisplayText():"");
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
        posTransactionEntity.setManualBill(false);
        posTransactionEntity.setMobileNO(getMvpView().getCustomerModule().getMobileNo());// user mobile number
        posTransactionEntity.setNetAmount((getMvpView().getOrderPriceInfoModel().getMrpTotalAmount() - getMvpView().getOrderPriceInfoModel().getTaxableTotalAmount()));// net amount
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
        posTransactionEntity.setRemainingamount(getMvpView().getOrderPriceInfoModel().getRoundedAmount());// amount to paid
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
        posTransactionEntity.setSalesLine(salesLineEntities());// sales line object
        posTransactionEntity.setSalesOrigin("");
        posTransactionEntity.setSEZ(0);
        posTransactionEntity.setShippingMethod("");
        posTransactionEntity.setStaff(getMvpView().getTransactionModule().getTerminalID());// terminal Id
        posTransactionEntity.setState(getDataManager().getGlobalJson().getStateCode());
        posTransactionEntity.setStockCheck(true);
        posTransactionEntity.setStore(getMvpView().getTransactionModule().getStoreID());// store details
        posTransactionEntity.setStoreName("");
        posTransactionEntity.setTenderLine(getTenderLine());// TenderLine Object
        posTransactionEntity.setTerminal(getMvpView().getTransactionModule().getTerminalID());// teinal id
        posTransactionEntity.setTimewhenTransClosed(0);
        posTransactionEntity.setTotalDiscAmount(0);
        posTransactionEntity.setTotalManualDiscountAmount(0);
        posTransactionEntity.setTotalManualDiscountPercentage(0);
        posTransactionEntity.setTotalMRP(getMvpView().getOrderPriceInfoModel().getMrpTotalAmount());// total MRP Price
        posTransactionEntity.setTotalTaxAmount(getMvpView().getOrderPriceInfoModel().getTaxableTotalAmount());// total Tax amount
        posTransactionEntity.setTrackingRef("");
        posTransactionEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());// Trancaction id
        posTransactionEntity.setTransDate(CommonUtils.getCurrentDate("dd-MMM-YYYY hh:mm:ss"));// payment date time format `18-Feb-2020 03:01:20`
        posTransactionEntity.setTransType(0);
        posTransactionEntity.setType(2);
        posTransactionEntity.setVendorId("");
        return posTransactionEntity;
    }

    private ArrayList<GenerateTenderLineReq.SalesLineEntity> salesLineEntities() {
        ArrayList<GenerateTenderLineReq.SalesLineEntity> salesLineEntities = new ArrayList<>();
        ArrayList<GetItemDetailsRes.Items> itemsArrayList = getMvpView().getSelectedProducts();
        for (GetItemDetailsRes.Items items : itemsArrayList) {
            if(!items.isItemDelete()) {
                GenerateTenderLineReq.SalesLineEntity salesLineEntity = new GenerateTenderLineReq.SalesLineEntity();
                salesLineEntity.setAdditionaltax(0);
                salesLineEntity.setApplyDiscount(false);
                salesLineEntity.setBarcode("");
                salesLineEntity.setBaseAmount(items.getBatchListObj().getMRP());
                salesLineEntity.setCategory(items.getCategory());
                salesLineEntity.setCategoryCode(items.getCategoryCode());
                salesLineEntity.setCategoryReference("");
                salesLineEntity.setCESSPerc(0);
                salesLineEntity.setCESSTaxCode("");
                salesLineEntity.setCGSTPerc(items.getBatchListObj().getCGSTPerc());
                salesLineEntity.setCGSTTaxCode(items.getBatchListObj().getCGSTTaxCode());
                salesLineEntity.setChecked(false);
                salesLineEntity.setComment("");
                salesLineEntity.setDiscAmount(0);
                salesLineEntity.setDiscId("");
                salesLineEntity.setDiscOfferId("");
                salesLineEntity.setDiscountStructureType(0);
                salesLineEntity.setDiscountType("");
                salesLineEntity.setDiseaseType("Acute");
                salesLineEntity.setDPCO(false);
                salesLineEntity.setExpiry(items.getBatchListObj().getExpDate());
                salesLineEntity.setHsncode_In(items.getHsncode_In());
                salesLineEntity.setIGSTPerc(items.getBatchListObj().getIGSTPerc());
                salesLineEntity.setIGSTTaxCode(items.getBatchListObj().getIGSTTaxCode());
                salesLineEntity.setInventBatchId(items.getBatchListObj().getBatchNo());
                salesLineEntity.setISPrescribed(0);
                salesLineEntity.setISReserved(false);
                salesLineEntity.setISStockAvailable(false);
                salesLineEntity.setItemId(items.getArtCode());
                salesLineEntity.setItemName(items.getDescription());
                salesLineEntity.setLineDiscPercentage(0);
                salesLineEntity.setLinedscAmount(0);
                salesLineEntity.setLineManualDiscountAmount(0);
                salesLineEntity.setLineManualDiscountPercentage(0);
                salesLineEntity.setLineNo(1);
                salesLineEntity.setManufacturerCode(items.getManufactureCode());
                salesLineEntity.setManufacturerName(items.getManufacture());
                salesLineEntity.setMixMode(false);
                salesLineEntity.setMMGroupId("0");
                salesLineEntity.setModifyBatchId("");
                salesLineEntity.setMRP(items.getBatchListObj().getMRP());
                salesLineEntity.setNetAmount(items.getBatchListObj().getPrice());
                salesLineEntity.setNetAmountInclTax(items.getBatchListObj().getMRP());
                salesLineEntity.setOfferAmount(0);
                salesLineEntity.setOfferDiscountType(0);
                salesLineEntity.setOfferDiscountValue(0);
                salesLineEntity.setOfferQty(0);
                salesLineEntity.setOfferType(0);
                salesLineEntity.setOmsLineID(0);
                salesLineEntity.setOmsLineRECID(0);
                salesLineEntity.setOrderStatus(0);
                salesLineEntity.setOriginalPrice(items.getBatchListObj().getMRP());
                salesLineEntity.setPeriodicDiscAmount(0);
                salesLineEntity.setPreviewText("");
                salesLineEntity.setPrice(items.getBatchListObj().getPrice());
                salesLineEntity.setPriceOverride(false);
                salesLineEntity.setProductRecID(items.getProductRecID());
                salesLineEntity.setQty(items.getBatchListObj().getEnterReqQuantity());
                salesLineEntity.setRemainderDays(0);
                salesLineEntity.setRemainingQty(0);
                salesLineEntity.setRetailCategoryRecID(items.getRetailCategoryRecID());
                salesLineEntity.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
                salesLineEntity.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
                salesLineEntity.setReturnQty(0);
                salesLineEntity.setScheduleCategory(items.getSch_Catg());
                salesLineEntity.setScheduleCategoryCode(items.getSch_Catg_Code());
                salesLineEntity.setSGSTPerc(items.getBatchListObj().getSGSTPerc());
                salesLineEntity.setSGSTTaxCode(items.getBatchListObj().getSGSTTaxCode());
                salesLineEntity.setStockQty(0);
                salesLineEntity.setSubCategory(items.getSubCategory());
                salesLineEntity.setSubCategoryCode(items.getSubCategory());
                salesLineEntity.setSubClassification(items.getSubClassification());
                salesLineEntity.setSubsitute(false);
                salesLineEntity.setSubstitudeItemId("");
                salesLineEntity.setTax(items.getBatchListObj().getTotalTax());
                salesLineEntity.setTaxAmount(items.getBatchListObj().getTotalTax());
                salesLineEntity.setTotal(items.getBatchListObj().getMRP());
                salesLineEntity.setTotalDiscAmount(0);
                salesLineEntity.setTotalDiscPct(0);
                salesLineEntity.setTotalRoundedAmount(0);
                salesLineEntity.setTotalTax(items.getBatchListObj().getTotalTax());
                salesLineEntity.setUnit("");
                salesLineEntity.setUnitPrice(items.getBatchListObj().getMRP());
                salesLineEntity.setUnitQty(items.getBatchListObj().getREQQTY());
                salesLineEntity.setVariantId("");
                salesLineEntities.add(salesLineEntity);
            }
        }
        return salesLineEntities;
    }

    private GenerateTenderLineReq.Wallet wallet (){
        GenerateTenderLineReq.Wallet wallet = new GenerateTenderLineReq.Wallet();
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
        wallet.setWalletAmount(0);
        wallet.setWalletOrderID("");
        wallet.setWalletRefundId("");
        wallet.setWalletRequestType(0);
        wallet.setWalletTransactionID(getMvpView().getValidateOneApolloPoints().getRRNO());
        wallet.setWalletType(0);
        wallet.setWalletURL("");
        return wallet;
    }

    private ArrayList<GenerateTenderLineRes.TenderLineEntity> getTenderLine(){
        return tenderLineEntities;
    }

    private ManualDiscCheckReq getManualDiscCheckReq(){
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
}
