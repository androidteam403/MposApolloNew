package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.app.Dialog;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.GetBatchDetailsByBarcodeRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
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

public class BatchlistScannerPresenter<V extends BatchlistScannerMvpView> extends BasePresenter<V>
        implements BatchlistScannerMvpPresenter<V> {
    @Inject
    public BatchlistScannerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickClose() {
        getMvpView().onClickClose();
    }

    @Override
    public void onClickSkip() {
        getMvpView().onClickSkip();
    }

    @Override
    public void onClickBarcodeProblem() {
        getMvpView().onClickBarcodeProblem();
    }

    @Override
    public void onAddItemsClicked() {
        getMvpView().onAddItemsClicked(false);
    }

    @Override
    public void getBatchDetailsByBarCode(String barcode, String itemId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetBatchDetailsByBarcodeRequest getBatchDetailsByBarcodeRequest = new GetBatchDetailsByBarcodeRequest();
            getBatchDetailsByBarcodeRequest.setArticleCode(itemId);
            getBatchDetailsByBarcodeRequest.setStoreId(getDataManager().getStoreId());
            getBatchDetailsByBarcodeRequest.setDataAreaId(getDataManager().getDataAreaId());
            getBatchDetailsByBarcodeRequest.setTerminalId(getDataManager().getTerminalId());
            getBatchDetailsByBarcodeRequest.setStoreState(getDataManager().getGlobalJson().getStateCode());
            getBatchDetailsByBarcodeRequest.setCustomerState(getDataManager().getGlobalJson().getStateCode());
            getBatchDetailsByBarcodeRequest.setSez(0);
            getBatchDetailsByBarcodeRequest.setSearchType(1);
            getBatchDetailsByBarcodeRequest.setExpiryDays(30);
            getBatchDetailsByBarcodeRequest.setBarcode(barcode);

            Call<GetBatchInfoRes> call = api.GET_BATCH_DETAILS_BY_BAR_CODE(getBatchDetailsByBarcodeRequest);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null) {
//                            if (isLastPos)
                            for (GetBatchInfoRes.BatchListObj item : response.body().getBatchList()) {
                                item.setBarcodeScannedBatch(true);
                            }
                            getMvpView().onSuccessGetBatchDetailsBarcode(response.body());
                        }
//                            else
//                                getMvpView().checkBatchInventoryFailed(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("InternetConnection Not Available");
        }
    }

    @Override
    public void onClickReferToAdmin() {
        getMvpView().onClickReferToAdmin();
    }

    @Override
    public void onClickOnHold() {
        getMvpView().onClickOnHold();
    }

    @Override
    public void getReasonList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ReasonListRequest request = new ReasonListRequest();
            request.setVendorId("6711");
            request.setStoreID(getDataManager().getStoreId());
            request.setDataAreaID("AHEL");
            request.setRequestType("1");
            request.setData(null);
            Call<ReasonListResponse> call = api.GET_REASON_LIST_API_CALL(request);
            call.enqueue(new Callback<ReasonListResponse>() {
                @Override
                public void onResponse(Call<ReasonListResponse> call, Response<ReasonListResponse> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body().getData() != null && response.body().getData().size() > 0) {
                            getMvpView().onSuccessGetResonListApiCall(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReasonListResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CheckBatchInventoryReq inventoryReq = new CheckBatchInventoryReq();
            inventoryReq.setDataAreaID(getDataManager().getDataAreaId());
            inventoryReq.setInventBatchID(item.getBatchNo());
            inventoryReq.setItemID(String.valueOf(item.getBatchId()));
            inventoryReq.setRequestStatus(0);
            inventoryReq.setReturnMessage("");
            inventoryReq.setStock(item.getREQQTY());
            inventoryReq.setStoreID(getDataManager().getStoreId());
            inventoryReq.setTerminalID(getDataManager().getTerminalId());

            Call<CheckBatchInventoryRes> call = api.CHECK_BATCH_INVENTORY_RES_CALL(inventoryReq);
            call.enqueue(new Callback<CheckBatchInventoryRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Response<CheckBatchInventoryRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
//                            if (isLastPos)
                            getMvpView().checkBatchInventorySuccess(response.body());
//                            else
//                                getMvpView().checkBatchInventoryFailed(response.body());
                    }
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
    public void onClickSearchTextClear() {
        getMvpView().onClickSearchTextClear();
    }

    @Override
    public void onClickProductInfo() {
        getMvpView().onClickProductInfo();
    }

    @Override
    public void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String reasonCode, Dialog dialog) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(getDataManager().getUserName());
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
                    order.setDataAreaID(getDataManager().getDataAreaId());
                    order.setStoreID(getDataManager().getStoreId());
                    order.setTerminalID(getDataManager().getTerminalId());
                    order.setTransactionID(selectedOmsHeaderList.get(i).getRefno());
                    order.setReasonCode(reasonCode);
                    ordersList.add(order);
                }
            }

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("MPOS/")) {
                replace_url = check_epos.replace("MPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");

            }
            ApiInterface api = ApiClient.getApiService(replace_url);
            String url = "";
            //getDataManager().getStoreId().equalsIgnoreCase("16001") &&
            if (getDataManager().getEposURL().equalsIgnoreCase("http://online.apollopharmacy.org:51/MPOS/")) {
                url = "OMSSERVICE/OMSService.svc/MPOSPickPackOrderReservation";
            } else {
                url = "OMSService.svc/MPOSPickPackOrderReservation";
            }
            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(url, mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse(@NotNull Call<MPOSPickPackOrderReservationResponse> call, @NotNull Response<MPOSPickPackOrderReservationResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body(), dialog);

                        }
//                        else {
//                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());
//                        }
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
    public String userName() {
        return getDataManager().getUserName();
    }

    @Override
    public String storeId() {
        return getDataManager().getStoreId();
    }

    @Override
    public String terminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public String eposUrl() {
        return getDataManager().getEposURL();
    }

    @Override
    public String dataAreaId() {
        return getDataManager().getDataAreaId();
    }

    @Override
    public String stateCode() {
        return getDataManager().getGlobalJson().getStateCode();
    }
}
