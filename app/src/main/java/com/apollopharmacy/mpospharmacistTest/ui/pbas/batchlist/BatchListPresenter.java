package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchListPresenter<V extends BatchListMvpView> extends BasePresenter<V>
        implements BatchListMvpPresenter<V> {
    @Inject
    public BatchListPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getBatchDetailsApi(GetOMSTransactionResponse.SalesLine itemId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
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
                        getMvpView().hideLoading();
                        if(response.isSuccessful() && response.body().getBatchList().size()==0 && response.body().getBatchList()==null){
                            getMvpView().onFailedBatchInfo(response.body());
                        }
                        else if (response.isSuccessful() && response.body().getBatchList() != null && response.body().getBatchList().size() > 0){
                            getMvpView().onSuccessBatchInfo(response.body().getBatchList());
                        }else{
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
    public void onAddItemsClicked() {
        getMvpView().onAddItemsClicked();
    }

    @Override
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj item, boolean isLastPos) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CheckBatchInventoryReq inventoryReq = new CheckBatchInventoryReq();
            inventoryReq.setDataAreaID("ahel");
            inventoryReq.setInventBatchID(item.getBatchNo());
            inventoryReq.setItemID(String.valueOf(item.getBatchId()));
            inventoryReq.setRequestStatus(0);
            inventoryReq.setReturnMessage("");
            inventoryReq.setStock(item.getREQQTY());
            inventoryReq.setStoreID("16001");
            inventoryReq.setTerminalID("005");

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
    public void onClickAutoUpdate() {
        getMvpView().onClickAutoUpdate();
    }

    @Override
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj items, Integer qty, String finalStatus) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
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
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)
                            getMvpView().checkBatchInventorySuccess(finalStatus, response.body());
                        else
                            getMvpView().checkBatchInventoryFailed(response.body() != null ? response.body().getReturnMessage() : "Stock not Available!");
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
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void onClickNotAvailableBtn() {
        getMvpView().onClickNotAvailableBtn();
    }
}
