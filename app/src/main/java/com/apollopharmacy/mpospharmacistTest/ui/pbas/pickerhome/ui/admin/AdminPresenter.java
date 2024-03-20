package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOmsTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPresenter<V extends AdminMvpView> extends BasePresenter<V> implements AdminMvpPresenter<V> {
    @Inject
    public AdminPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    public static boolean isFirstTime = true;
    @Override
    public void getOmsTransactionHeader(String isMpos) {
        if (getMvpView().isNetworkConnected()) {
            if (isFirstTime) {
                getMvpView().showLoading();
            }
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetOMSTransactionHeaderRequest getOMSTransactionHeaderRequest = new GetOMSTransactionHeaderRequest();
            getOMSTransactionHeaderRequest.setIsMPOS(isMpos);
            getOMSTransactionHeaderRequest.setUserName("");
            getOMSTransactionHeaderRequest.setTransactionID("");
            getOMSTransactionHeaderRequest.setRefID("");
            getOMSTransactionHeaderRequest.setExpiryDays(90);
            getOMSTransactionHeaderRequest.setStoreID(getDataManager().getStoreId());
            getOMSTransactionHeaderRequest.setTerminalID(getDataManager().getTerminalId());
            getOMSTransactionHeaderRequest.setDataAreaID(getDataManager().getDataAreaId());
            Call<GetOMSTransactionHeaderResponse> call = apiInterface.getOmsTransactionHeaderApiCall(getOMSTransactionHeaderRequest);
            call.enqueue(new Callback<GetOMSTransactionHeaderResponse>() {
                @Override
                public void onResponse(Call<GetOMSTransactionHeaderResponse> call, Response<GetOMSTransactionHeaderResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (!isFirstTime) {
                                getMvpView().hideLoading();
                            }
                            getMvpView().onSuccessGetOmsTransactionHeader(response.body().getOMSHeader(), isMpos);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetOMSTransactionHeaderResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClickAllOrders() {
        getMvpView().onClickAllOrders();
    }
}
