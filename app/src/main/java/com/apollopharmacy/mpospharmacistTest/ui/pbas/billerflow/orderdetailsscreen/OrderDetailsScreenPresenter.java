package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.model.PostTransactionEntityReq;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOmsTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsScreenPresenter<V extends OrderDetailsScreenMvpView> extends BasePresenter<V>
        implements OrderDetailsScreenMvpPresenter<V> {

    @Inject
    public OrderDetailsScreenPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onMinusCustomerDetails() {
        getMvpView().onMinusCustomerDetails();
    }

    @Override
    public void onPlusCustomerDetails() {
        getMvpView().onPlusCustomerDetails();
    }

    @Override
    public void onminusOrderDetails() {
        getMvpView().onminusOrderDetails();
    }

    @Override
    public void getTransactionID() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            TransactionIDReqModel transactionIDModel = new TransactionIDReqModel();
            transactionIDModel.setRequestStatus(0);
            transactionIDModel.setReturnMessage("");
            transactionIDModel.setResultValue("");
            transactionIDModel.setTransactionID("");
            transactionIDModel.setStoreID(getDataManager().getStoreId());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getDataManager().getDataAreaId());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {
                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().showTransactionID(response.body());
                        getCorporateList();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TransactionIDResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void UpdateOmsOrder(OMSOrderUpdateRequest omsOrderUpdateRequest) {
        {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                omsOrderUpdateRequest.setTerminalID(getDataManager().getTerminalId());
                //ApiInterface api = ApiClient.getApiService(Constant.UPDATEOMSORDER);
                // text.replace("/"","");
                String check_epos = getDataManager().getEposURL();
                String replace_url = getDataManager().getEposURL();
                if (check_epos.contains("EPOS/")) {
                    replace_url = check_epos.replace("EPOS/", "");

                }
                if (check_epos.contains("9880")) {
                    replace_url = check_epos.replace("9880", "9887");

                }
                // ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
                ApiInterface api = ApiClient.getApiService(replace_url);

                // ApiInterface api = ApiClient.getApiService3();
                Call<OMSOrderUpdateResponse> call = api.UPDATE_OMS_ORDER(omsOrderUpdateRequest);
                call.enqueue(new Callback<OMSOrderUpdateResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<OMSOrderUpdateResponse> call, @NotNull Response<OMSOrderUpdateResponse> response) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getRequestStatus() == 0) {
                                getMvpView().OmsOrderUpdateSuccess(response.body());
                            } else {
                                getMvpView().OmsOrderUpdateFailure(response.body());
                            }

                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<OMSOrderUpdateResponse> call, @NotNull Throwable t) {
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
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            //getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().getCorporateList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onLoadOmsOrder(CustomerDataResBean customerDataResBean) {

    }



    @Override
    public void onCheckStock(GetOMSTransactionResponse response) {

    }

    PostTransactionEntityReq postTransactionEntityData = new PostTransactionEntityReq();


    @Override
    public void onplusOrderDetails() {
        getMvpView().onplusOrderDetails();
    }

    @Override
    public void onminusVendorDetails() {
        getMvpView().onminusVendorDetails();
    }

    @Override
    public void fetchOMSCustomerInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            GetOmsTransactionRequest getOmsTransactionRequest = new GetOmsTransactionRequest();
            getOmsTransactionRequest.setDataAreaID("ahel");
            getOmsTransactionRequest.setExpiryDays(90);
            getOmsTransactionRequest.setRefID("");
            getOmsTransactionRequest.setStoreID(getDataManager().getStoreId());
            getOmsTransactionRequest.setTerminalID(getDataManager().getTerminalId());
            getOmsTransactionRequest.setTransactionID(refNumber);
            Call<List<GetOMSTransactionResponse>> call = apiInterface.getOmsApiCall(getOmsTransactionRequest);
            call.enqueue(new Callback<List<GetOMSTransactionResponse>>() {
                @Override
                public void onResponse(Call<List<GetOMSTransactionResponse>> call, Response<List<GetOMSTransactionResponse>> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().onSuccessGetOMSTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<GetOMSTransactionResponse>> call, Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onPlusVendorDetails() {
        getMvpView().onPlusVendorDetails();
    }

    @Override
    public void getBatchDetailsApi(List<GetOMSTransactionResponse.SalesLine> selected_item, List<GetOMSTransactionResponse.PickPackReservation> pickPackReservation) {

    }

    @Override
    public void onActionsContinue() {
        getMvpView().onActionsContinue();
    }

    @Override
    public void onGenerateBill() {
        getMvpView().onGenerateBill();
    }

    @Override
    public void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader omsHeader) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(getDataManager().getUserName());
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            if (omsHeader != null) {
                MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
                order.setDataAreaID("AHEL");
                order.setStoreID(getDataManager().getStoreId());
                order.setTerminalID(getDataManager().getTerminalId());
                order.setTransactionID(omsHeader.getRefno());
                ordersList.add(order);
            }

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");
                replace_url = check_epos.replace("9880", "9887");

            }
            ApiInterface api = ApiClient.getApiService(replace_url);

            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse(@NotNull Call<MPOSPickPackOrderReservationResponse> call, @NotNull Response<MPOSPickPackOrderReservationResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());

                        } else {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<MPOSPickPackOrderReservationResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public void onPrintLabel() {
        getMvpView().onPrintLabel();
    }

    @Override
    public void onPrintShippingLabel() {
        getMvpView().onPrintShippingLabel();
    }

    @Override
    public void onSendBacktoPackerLabel() {
        getMvpView().onSendBacktoPackerLabel();
    }
}

