package com.apollopharmacy.mpospharmacist.ui.storesetup;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupReqModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreSetupPresenter<V extends StoreSetupMvpView> extends BasePresenter<V>
        implements StoreSetupMvpPresenter<V> {
    @Inject
    public StoreSetupPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSelectStoreSearch() {
        getMvpView().onSelectStoreSearch();
    }

    @Override
    public void onSaveBtnClick() {
        getMvpView().onSaveBtnClick();
    }

    @Override
    public void onCancelBtnClick() {
        getMvpView().onCancelBtnClick();
    }

    @Override
    public void handleStoreSetupService() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService2();
            DeviceSetupReqModel storeSetupReqModel = new DeviceSetupReqModel();
            storeSetupReqModel.setMACID(getMvpView().getDeviceId());
            storeSetupReqModel.setFCMKEY(getMvpView().getFcmKey());
            storeSetupReqModel.setSTOREID(getMvpView().getStoreId());
            storeSetupReqModel.setTERMINALID(getMvpView().getTerminalId());
            storeSetupReqModel.setUSERID(getMvpView().getUserId());
            storeSetupReqModel.setDEVICETYPE(getMvpView().getDeviceType());
            storeSetupReqModel.setDEVICEDATE(getMvpView().getRegisteredDate());
            storeSetupReqModel.setLATITUDE(getMvpView().getLatitude());
            storeSetupReqModel.setLOGITUDE(getMvpView().getLongitude());
            Call<DeviceSetupResModel> call = api.STORE_SETUP_CALL(storeSetupReqModel);
            call.enqueue(new Callback<DeviceSetupResModel>() {
                @Override
                public void onResponse(@NotNull Call<DeviceSetupResModel> call, @NotNull Response<DeviceSetupResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if(response.body().isStatus()){
                            getMvpView().storeSetupSuccess(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DeviceSetupResModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getStoreList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService2();
            Call<StoreListResponseModel> call = api.GET_STORES_LIST();
            call.enqueue(new Callback<StoreListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<StoreListResponseModel> call, @NotNull Response<StoreListResponseModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        getMvpView().setStoresList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<StoreListResponseModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void insertAdminLoginDetails() {
        getDataManager().setAdminSetUpFinish(true);
        getMvpView().onNavigateHomeScreen();
    }
}
