package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOmsTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpVerificationPresenter<V extends PickUpVerificationMvpView> extends BasePresenter<V>
        implements PickUpVerificationMvpPresenter<V> {

    @Inject
    public PickUpVerificationPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onPartialWarningYesClick() {
        getMvpView().onPartialWarningYesClick();
    }

    @Override
    public void onPartialWarningNoClick() {
        getMvpView().onPartialWarningNoClick();
    }

    @Override
    public void onClickReVerificatio() {
        getMvpView().onClickReVerificatio();
    }


    @Override
    public void fetchOMSCustomerInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            GetOmsTransactionRequest getOmsTransactionRequest = new GetOmsTransactionRequest();
            getOmsTransactionRequest.setDataAreaID("ahel");
            getOmsTransactionRequest.setExpiryDays(90);
            getOmsTransactionRequest.setRefID("");
            getOmsTransactionRequest.setStoreID(getDataManager().getStoreId());
            getOmsTransactionRequest.setTerminalID(getDataManager().getTerminalId());
            getOmsTransactionRequest.setTransactionID(refNumber);
            Call<List<GetOMSTransactionResponse>> call = apiInterface.getOmsApiCall(getOmsTransactionRequest);
            call.enqueue(new Callback<List<GetOMSTransactionResponse>>() {
                @Override
                public void onResponse(Call<List<GetOMSTransactionResponse>> call, Response<List<GetOMSTransactionResponse>> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().onSuccessGetOMSTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<GetOMSTransactionResponse>> call, Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }


    @Override
    public void onClickVerification() {
        getMvpView().onClickVerification();
    }

}
