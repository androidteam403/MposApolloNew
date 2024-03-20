package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrdersPresenter<V extends AllOrdersMvpView> extends BasePresenter<V> implements AllOrdersMvpPresenter<V> {
    @Inject
    public AllOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void getOmsTransactionHeader() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetOMSTransactionHeaderRequest getOMSTransactionHeaderRequest = new GetOMSTransactionHeaderRequest();
            getOMSTransactionHeaderRequest.setIsMPOS("2");
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
                            getMvpView().hideLoading();
                            getMvpView().onSuccessGetOmsTransactionHeader(response.body().getOMSHeader());
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetOMSTransactionHeaderResponse> call, Throwable t) {

                }
            });
        }
    }
}
