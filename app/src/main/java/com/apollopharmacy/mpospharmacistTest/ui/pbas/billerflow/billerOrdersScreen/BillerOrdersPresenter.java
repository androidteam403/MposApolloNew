package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.util.Log;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillerOrdersPresenter<V extends BillerOrdersMvpView> extends BasePresenter<V>
        implements BillerOrdersMvpPresenter<V> {

    @Inject
    public BillerOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
        int i = 0;
    }


    @Override
    public void onClickFilterIcon() {
        getMvpView().onClickFilterIcon();
    }

    @Override
    public void fetchFulfilmentOrderList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            OMSTransactionHeaderReqModel reqModel = new OMSTransactionHeaderReqModel();
            reqModel.setTransactionID("");
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            reqModel.setIsMPOS("2");//3
            reqModel.setUserName(getDataManager().getUserName());

            Call<OMSTransactionHeaderResModel> call = api.GET_OMS_TRANSACTION_HEADER(reqModel);
            call.enqueue(new Callback<OMSTransactionHeaderResModel>() {
                @Override
                public void onResponse(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Response<OMSTransactionHeaderResModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful())
                        getMvpView().onSucessfullFulfilmentIdList(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }


//        if (getMvpView().isNetworkConnected()) {
//            getMvpView().showLoading();
//            getMvpView().hideKeyboard();
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            TransactionHeaderRequest reqModel = new TransactionHeaderRequest();
//            reqModel.setTransactionID("");
//            reqModel.setRefID("");
//            reqModel.setExpiryDays(90);
//            reqModel.setStoreID(getDataManager().getStoreId());
//            reqModel.setTerminalID(getDataManager().getTerminalId());
//            reqModel.setDataAreaID(getDataManager().getDataAreaId());
//            reqModel.setIsMPOS(getDataManager().getGlobalJson().getMPOSVersion());
//            reqModel.setUserName(getDataManager().getUserName());
//            Call<TransactionHeaderResponse> call = apiInterface.GET_OMS_TRANSACTION_HEADER_PICKER(reqModel);
//            call.enqueue(new Callback<TransactionHeaderResponse>() {
//                @Override
//                public void onResponse(Call<TransactionHeaderResponse> call, Response<TransactionHeaderResponse> response) {
//                    getMvpView().hideLoading();
//                    if (response.isSuccessful()) {
//                        if (response.body() != null)
//                            getMvpView().onSucessfullFulfilmentIdList(response.body());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TransactionHeaderResponse> call, Throwable t) {
//                    getMvpView().hideLoading();
//                    handleApiError(t);
//                }
//            });
//        }

    }


    @Override
    public void onclickScanCode() {
        getMvpView().onclickScanCode();
    }

    @Override
    public List<OMSTransactionHeaderResModel.OMSHeaderObj> getTotalOmsHeaderList() {
        return getDataManager().getTotalOmsHeaderListObj();
    }

    @Override
    public void setTotalOmsHeaderList(List<OMSTransactionHeaderResModel.OMSHeaderObj> totalOmsHeaderList) {
        getDataManager().setTotalOmsTransactionHeaderObj(totalOmsHeaderList);
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
}

