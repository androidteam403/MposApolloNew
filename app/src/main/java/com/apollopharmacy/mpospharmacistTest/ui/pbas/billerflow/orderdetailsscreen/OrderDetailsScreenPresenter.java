package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.model.PostTransactionEntityReq;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
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
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        } else {
                            getMvpView().showMessage(R.string.some_error);
                        }
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
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
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
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            customerDataResBean.setTerminal(getDataManager().getTerminalId());
            customerDataResBean.setCreatedonPosTerminal(getDataManager().getTerminalId());

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = api.LOAD_OMS_ORDER(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().LoadOmsOrderSuccess(response.body());
                        } else {
                            getMvpView().LoadOmsOrderFailure(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerDataResBean> call, @NotNull Throwable t) {
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
    public void onCheckBatchStock(CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = api.omscheckbatchstock(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().CheckBatchStockSuccess(response.body());
                        } else {
                            getMvpView().CheckBatchStockFailure(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerDataResBean> call, @NotNull Throwable t) {
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
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CustomerDataReqBean reqModel = new CustomerDataReqBean();
            reqModel.setTransactionID(refNumber);
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<ArrayList<CustomerDataResBean>> call = api.GET_OMS_TRANSACTION(reqModel);
            call.enqueue(new Callback<ArrayList<CustomerDataResBean>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Response<ArrayList<CustomerDataResBean>> response) {
                    if (response.isSuccessful() && response.body() != null) {
//                        fetchOMSMedicineInfo(refNumber);
                        //System.out.println("Customer Name-->0"+response.body().get(0).getCustomerName());
                        getMvpView().onSuccessGetOMSTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    System.out.println("Customer Name-->4" + "Syntax Error");

                    handleApiError(t);
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
    public void getBatchDetailsApi(String itemId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(itemId);
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState(getDataManager().getGlobalJson().getStateCode());
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            batchInfoReq.setExpiryDays(getDataManager().getGlobalJson().getOMSExpiryDays());
            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
//                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessBatchInfo(response.body());
                        } else {
                            getMvpView().onFailedBatchInfo(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
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

    @Override
    public void onClickProceedAction() {
        getMvpView().onClickProceedAction();
    }

    @Override
    public void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            omsOrderForwardRequest.setTerminalID(getDataManager().getTerminalId());

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
            Call<OMSOrderForwardResponse> call = api.UPDATE_OMS_ORDER(omsOrderForwardRequest);
            call.enqueue(new Callback<OMSOrderForwardResponse>() {
                @Override
                public void onResponse(@NotNull Call<OMSOrderForwardResponse> call, @NotNull Response<OMSOrderForwardResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().OmsOrderUpdateSuccess(response.body(), omsOrderForwardRequest.getRequestType());

                        } else {
                            getMvpView().OmsOrderUpdateFailure(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<OMSOrderForwardResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public void onClickSendtoPacker() {
        getMvpView().onClickSendtoPacker();
    }

    @Override
    public void onClickContinueBill() {
        getMvpView().onClickContinueBill();
    }
}

