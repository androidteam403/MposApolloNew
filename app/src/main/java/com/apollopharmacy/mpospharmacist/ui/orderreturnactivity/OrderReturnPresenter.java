package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.TrackingWiseReturnAllowedRes;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderReturnPresenter<V extends OrederReturnMvpView> extends BasePresenter<V>
        implements OrderReturnMvpPresenter<V> {

    @Inject
    public OrderReturnPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBackPressed() {
        getMvpView().onClickActionBarBack();
    }

    @Override
    public void trackingWiseReturnAllowed(String corpId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            Call<TrackingWiseReturnAllowedRes> call = api.TRACKING_WISE_RETURN_ALLOWED_RES_CALL(corpId);
            call.enqueue(new Callback<TrackingWiseReturnAllowedRes>() {
                @Override
                public void onResponse(@NotNull Call<TrackingWiseReturnAllowedRes> call, @NotNull Response<TrackingWiseReturnAllowedRes> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        if(response.body().getResultValue().equalsIgnoreCase("1")){
                            getMvpView().isCorpAllowedReturn(true);
                        }else{
                            getMvpView().isCorpAllowedReturn(false);
                        }

                    } else {
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TrackingWiseReturnAllowedRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void cancelDSBilling(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();

            Call<CalculatePosTransactionRes> call = api.CANCEL_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("Order Cancelled", "Receipt Number: " + response.body().getReturnReceiptId());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onReturnClick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().partialReturnOrder();
    }

    @Override
    public void onCancelCLick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().showInfoPopup("Cancel Order", "Do you want to Cancel Order?", true,false);
    }

    @Override
    public void onReOrderClick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().showInfoPopup("Order Return All", "Do you want to Return order?", false,true);
    }

    @Override
    public void orderReturnAll(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            posTransactionRes.setReturn(true);
            posTransactionRes.setReturnStore(getDataManager().getGlobalJson().getStoreID());
            posTransactionRes.setReturnTerminal(getDataManager().getTerminalId());
            Call<CalculatePosTransactionRes> call = api.RETURN_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("Return successfully ", "Receipt Number: " + response.body().getReturnReceiptId());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
