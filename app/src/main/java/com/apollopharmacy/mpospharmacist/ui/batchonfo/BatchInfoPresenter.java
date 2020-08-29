package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.expirymodel.ExpiryChangeReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.expirymodel.ExpiryChangeRes;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
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
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
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
                        if (response.isSuccessful() && response.body() != null)
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

    @Override
    public String getStoreName() {
        return getDataManager().getUserName();
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
    public void expiryChangeApi() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ExpiryChangeReq expiryChangeReq = new ExpiryChangeReq();
            expiryChangeReq.setDataAreaID(getDataManager().getDataAreaId());
            expiryChangeReq.setRequestStatus(0);
            expiryChangeReq.setReturnMessage("");
            expiryChangeReq.setStore(getDataManager().getStoreId());
            expiryChangeReq.setUserID(getDataManager().getUserId());
            expiryChangeReq.setTerminalID(getDataManager().getTerminalId());
            List<ExpiryChangeReq.BatchListExpiryObj> expiryObjList = new ArrayList<>();

            for (int i = 0; i < getMvpView().getbatchInfoRes().size(); i++) {
                ExpiryChangeReq.BatchListExpiryObj entity = new ExpiryChangeReq.BatchListExpiryObj();
                entity.setBatchNo(getMvpView().getbatchInfoRes().get(i).getBatchNo());
                entity.setCESSPerc((int) getMvpView().getbatchInfoRes().get(i).getCESSPerc());
                entity.setCESSTaxCode(getMvpView().getbatchInfoRes().get(i).getCESSTaxCode());
                entity.setCGSTPerc((int) getMvpView().getbatchInfoRes().get(i).getCGSTPerc());
                entity.setCGSTTaxCode(getMvpView().getbatchInfoRes().get(i).getCGSTTaxCode());
                entity.setIGSTPerc((int) getMvpView().getbatchInfoRes().get(i).getIGSTPerc());
                entity.setIGSTTaxCode(getMvpView().getbatchInfoRes().get(i).getIGSTTaxCode());
                entity.setISMRPChange(getMvpView().getbatchInfoRes().get(i).getISMRPChange());
                entity.setItemID(getMvpView().getbatchInfoRes().get(i).getItemID());
                entity.setExpDate(getMvpView().getbatchInfoRes().get(i).getExpDate());
                entity.setMRP((int) getMvpView().getbatchInfoRes().get(i).getMRP());
                entity.setTotalTax((int) getMvpView().getbatchInfoRes().get(i).getTotalTax());
                entity.setNearByExpiry(getMvpView().getbatchInfoRes().get(i).getNearByExpiry());
                entity.setPrice((int) getMvpView().getbatchInfoRes().get(i).getPrice());
                entity.setQ_O_H(getMvpView().getbatchInfoRes().get(i).getQ_O_H());
                entity.setREQQTY(getMvpView().getbatchInfoRes().get(i).getREQQTY());
                entity.setSGSTPerc((int) getMvpView().getbatchInfoRes().get(i).getSGSTPerc());
                entity.setSGSTTaxCode(getMvpView().getbatchInfoRes().get(i).getSGSTTaxCode());
                entity.setSNO(getMvpView().getbatchInfoRes().get(i).getSNO());
                expiryObjList.add(entity);
            }
            expiryChangeReq.setBatchList(expiryObjList);

            Call<ExpiryChangeRes> call = api.EXPIRY_CHANGE_RES_CALL(expiryChangeReq);
            call.enqueue(new Callback<ExpiryChangeRes>() {
                @Override
                public void onResponse(@NotNull Call<ExpiryChangeRes> call, @NotNull Response<ExpiryChangeRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().checkExpiryDateChangeSuccess(response.body());
                        } else
                            getMvpView().checkBatchInventoryFailed(response.body() != null ? response.body().getReturnMessage() : "Stock not Available!");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ExpiryChangeRes> call, @NotNull Throwable t) {
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
