package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;

import android.util.Log;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupProcessPresenter<V extends PickupProcessMvpView> extends BasePresenter<V> implements PickupProcessMvpPresenter<V> {
    @Inject
    public PickupProcessPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void onClickContinue() {
        getMvpView().onClickContinue();
    }

    @Override
    public void onRackApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RacksDataResponse> call = apiInterface.doRackApiCall();
            call.enqueue(new retrofit2.Callback<RacksDataResponse>() {
                @Override
                public void onResponse(Call<RacksDataResponse> call, Response<RacksDataResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().hideLoading();
                            getMvpView().onSuccessRackApi(response.body());
                        }
                    }
                    Log.e("TAG", response.code() + "");
                }

                @Override
                public void onFailure(Call<RacksDataResponse> call, Throwable t) {
                    Log.e("Service", "Failed res :: " + t.getMessage());
                    getMvpView().hideLoading();
                    getMvpView().showMessage("Something went wrong");
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickStausIcon() {
        getMvpView().onClickStausIcon();
    }

    @Override
    public void onClickFullPicked() {
        getMvpView().onClickFullPicked();
    }

    @Override
    public void onClickPartialPicked() {
        getMvpView().onClickPartialPicked();
    }

    @Override
    public void onClickNotAvailable() {
        getMvpView().onClickNotAvailable();
    }

    @Override
    public void onClickSkip() {
        getMvpView().onClickSkip();
    }

    @Override
    public void getBatchDetailsApiCall(GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader) {
        if (getMvpView().isNetworkConnected()) {
           getMvpView().showLoading();
            getMvpView().hideKeyboard();
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(salesLine.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState("AP");
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            batchInfoReq.setExpiryDays(90);

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new retrofit2.Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(Call<GetBatchInfoRes> call, Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().hideLoading();
                            getMvpView().onSuccessGetBatchDetails(response.body(), salesLine, refNo, orderAdapterPos, position, omsHeader);
                        }
                    }
                    Log.e("TAG", response.code() + "");
                }

                @Override
                public void onFailure(Call<GetBatchInfoRes> call, Throwable t) {
                    Log.e("Service", "Failed res :: " + t.getMessage());
                    getMvpView().hideLoading();
                    getMvpView().showMessage("Something went wrong");
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


    public void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId) {
        if (getMvpView().isNetworkConnected()) {
//          getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(itemId.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId("ahel");
            batchInfoReq.setExpiryDays(90);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setSearchType(1);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState("AP");
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());

            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog

                        if(response.isSuccessful() && response.body().getBatchList().size()==0 && response.body().getBatchList()==null){
                            getMvpView().onFailedBatchInfo(response.body());
                        }
                        else if (response.isSuccessful() && response.body().getBatchList() != null && response.body().getBatchList().size() > 0){
                            getMvpView().onSuccessBatchInfo(response.body().getBatchList());
                        }else{
                            getMvpView().onFailedBatchInfo(response.body());
                        }

                    }
//                    getMvpView().hideLoading();
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

//    @Override
//    public void onClickBatchDetails() {
//        getMvpView().onClickBatchDetails();
//    }

    @Override
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj items, int qty, String finalStatus) {

        if (getMvpView().isNetworkConnected()) {
//            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CheckBatchInventoryReq inventoryReq = new CheckBatchInventoryReq();
            inventoryReq.setDataAreaID(getDataManager().getDataAreaId());
            inventoryReq.setInventBatchID(items.getBatchNo());
            inventoryReq.setItemID(items.getItemID());
            inventoryReq.setRequestStatus(0);
            inventoryReq.setReturnMessage("");
            inventoryReq.setStock(qty);
            inventoryReq.setStoreID(getDataManager().getStoreId());
            inventoryReq.setTerminalID(getDataManager().getTerminalId());

            Call<CheckBatchInventoryRes> call = api.CHECK_BATCH_INVENTORY_RES_CALL(inventoryReq);
            call.enqueue(new Callback<CheckBatchInventoryRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Response<CheckBatchInventoryRes> response) {
                    if (response.isSuccessful()) {

                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().checkBatchInventorySuccess(finalStatus, response.body());
                        else
                            getMvpView().checkBatchInventoryFailed(response.body() != null ? response.body().getReturnMessage() : "Stock not Available!");
                    }

//                    getMvpView().hideLoading();
                }

                @Override
                public void onFailure(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("InternetConnection Not Available");
        }
    }

    @Override
    public void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(getDataManager().getUserName());
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
                    order.setDataAreaID("AHEL");
                    order.setStoreID(getDataManager().getStoreId());
                    order.setTerminalID(getDataManager().getTerminalId());
                    order.setTransactionID(selectedOmsHeaderList.get(i).getRefno());
                    ordersList.add(order);
                }
            }

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
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
    public void onClickForwardToPacker() {
        getMvpView().onClickForwardToPacker();
    }
}

