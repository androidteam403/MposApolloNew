package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.TrackingWiseReturnAllowedRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
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
    public GetGlobalConfingRes getGlobalConfing() {
       return getDataManager().getGlobalJson();
    }

    @Override
    public String terminalId() {
        return getDataManager().getTerminalId();
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
                   // getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        if(response.body().getResultValue().equalsIgnoreCase("True") || response.body().getResultValue().equalsIgnoreCase("true") ){
                            getMvpView().isCorpAllowedReturn(true);
                            getTransactionID();
                        }else{
                            getMvpView().hideLoading();
                            getMvpView().isCorpAllowedReturn(false);
                        }

                    } else {
                        getMvpView().hideLoading();
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
    public void getTransactionID() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            TransactionIDReqModel transactionIDModel = new TransactionIDReqModel();
            transactionIDModel.setRequestStatus(0);
            transactionIDModel.setReturnMessage("");
            transactionIDModel.setResultValue("");
            transactionIDModel.setTransactionID("");
            transactionIDModel.setStoreID(getGlobalConfing().getStoreID());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getGlobalConfing().getDataAreaID());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {
                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().setTransactionId(response.body().getTransactionID());
                    }else{
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }else{
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
    public void cancelDSBilling(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            posTransactionRes.setReturn(true);
            posTransactionRes.setReturnStore(getDataManager().getGlobalJson().getStoreID());
            posTransactionRes.setReturnTerminal(getDataManager().getTerminalId());
            posTransactionRes.setState(getGlobalConfing().getStateCode());
            Call<CalculatePosTransactionRes> call = api.CANCEL_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
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

    @Override
    public void onReturnClick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().partialReturnOrder();
    }

    @Override
    public void onCancelCLick(CalculatePosTransactionRes posTransactionRes) {
      //  if (isAllowOrNot(posTransactionRes)) {
            getMvpView().showInfoPopup("Cancel Order", "Do you want to Cancel Order?", true, false);
//        }else{
//            getMvpView().showCancelOrderSuccess("","Transaction Already Cancelled!!");
//        }
    }

    @Override
    public void onReOrderClick(CalculatePosTransactionRes posTransactionRes) {
       // if (isAllowOrNot(posTransactionRes)) {
            getMvpView().showInfoPopup("Order Return All", "Do you want to Return order?", false, true);
//        } else {
//            getMvpView().showCancelOrderSuccess("", "Transaction Already Return!!");
//        }
    }

    @Override
    public boolean isAllowOrNot(CalculatePosTransactionRes posTransactionRes){
        for(SalesLineEntity salesLineEntity : posTransactionRes.getSalesLine()){
            if(salesLineEntity.getQty() != salesLineEntity.getRemainingQty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void orderReturnAll(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            posTransactionRes.setReturn(true);
            posTransactionRes.setReturnStore(getDataManager().getGlobalJson().getStoreID());
            posTransactionRes.setReturnTerminal(getDataManager().getTerminalId());
            posTransactionRes.setState(getGlobalConfing().getStateCode());
            Call<CalculatePosTransactionRes> call = api.RETURN_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
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
