package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchInfoPresenter<V extends BatchInfoMvpView> extends BasePresenter<V>
        implements BatchInfoMvpPresenter<V> {
    @Inject
    public BatchInfoPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickIncrement() {
        getMvpView().onIncrementClick();
    }

    @Override
    public void onClickDecrement() {
        getMvpView().onDecrementClick();
    }

    @Override
    public void onNavigateNextActivity() {
        getMvpView().onNavigateNextActivity();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void getBatchDetailsApi(SalesLineEntity selected_item) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(selected_item.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState(getDataManager().getGlobalJson().getStateCode());
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0)
                            getMvpView().onSuccessBatchInfo(response.body());
                        else
                            getMvpView().onFailedBatchInfo(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
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
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj items) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            CheckBatchInventoryReq inventoryReq = new CheckBatchInventoryReq();
            inventoryReq.setDataAreaID(getDataManager().getDataAreaId());
            inventoryReq.setInventBatchID(items.getBatchNo());
            inventoryReq.setItemID(items.getItemID());
            inventoryReq.setRequestStatus(0);
            inventoryReq.setReturnMessage("");
            inventoryReq.setStock(items.getREQQTY());
            inventoryReq.setStoreID(getDataManager().getStoreId());
            inventoryReq.setTerminalID(getDataManager().getTerminalId());

            Call<CheckBatchInventoryRes> call = api.CHECK_BATCH_INVENTORY_RES_CALL(inventoryReq);
            call.enqueue(new Callback<CheckBatchInventoryRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Response<CheckBatchInventoryRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0)
                            getMvpView().checkBatchInventorySuccess();
                        else
                            getMvpView().checkBatchInventoryFailed(response.body() != null ? response.body().getReturnMessage() : "Stock not Available!");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("InternetConnection Not Available");
        }
    }
}
