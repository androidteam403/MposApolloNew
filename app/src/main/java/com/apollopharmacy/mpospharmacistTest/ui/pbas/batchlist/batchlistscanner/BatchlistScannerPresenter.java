package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.GetBatchDetailsByBarcodeRequest;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

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
}
