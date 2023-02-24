package com.apollopharmacy.mpospharmacistTest.ui.storesetup;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.ConfingReq;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.ConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreSetupPresenter<V extends StoreSetupMvpView> extends BasePresenter<V> implements StoreSetupMvpPresenter<V> {

    @Inject
    public StoreSetupPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSelectStoreSearch() {
        getMvpView().onSelectStoreSearch();
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
            storeSetupReqModel.setUSERID(getDataManager().getAdminLoginId());
            storeSetupReqModel.setDEVICETYPE(getMvpView().getDeviceType());

            storeSetupReqModel.setDEVICEDATE(CommonUtils.getDateyyyymmddFormat(getMvpView().getRegisteredDate()));

//            storeSetupReqModel.setDEVICEDATE(getMvpView().getRegisteredDate());
            storeSetupReqModel.setLATITUDE(getMvpView().getLatitude());
            storeSetupReqModel.setLOGITUDE(getMvpView().getLongitude());
            Call<DeviceSetupResModel> call = api.STORE_SETUP_CALL(storeSetupReqModel);
            call.enqueue(new Callback<DeviceSetupResModel>() {
                @Override
                public void onResponse(@NotNull Call<DeviceSetupResModel> call, @NotNull Response<DeviceSetupResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body().isStatus()) {
                            if (getMvpView().getStoreContactNum() != null) {

                                getDataManager().setBranchPhoneNumber(getMvpView().getStoreContactNum());
                            }
                            getDataManager().setStoreId(getMvpView().getStoreId());
                            getDataManager().setTerminalId(getMvpView().getTerminalId());
                            getDataManager().setDataAreaId(getMvpView().getStoreDetails().getDataAreaId());
                            getDataManager().setAdminSetUpFinish(true);
                            getMvpView().storeSetupSuccess(response.body());
                        } else {
                            getMvpView().showMessage(response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DeviceSetupResModel> call, @NotNull Throwable t) {
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
    public void getStoreList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService2();
            Call<StoreListResponseModel> call = api.GET_STORES_LIST();
            call.enqueue(new Callback<StoreListResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<StoreListResponseModel> call, @NotNull Response<StoreListResponseModel> response) {
                    if (response.isSuccessful()) {
                        if (getMvpView() != null) {
                            getMvpView().hideLoading();
                            getMvpView().setStoresList(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<StoreListResponseModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
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

    @Override
    public void checkConfingApi() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getMvpView().getEposURL());
            ConfingReq confingReq = new ConfingReq();
            confingReq.setDataAreaID(getMvpView().getStoreDetails().getDataAreaId());
            confingReq.setRequestStatus(0);
            confingReq.setStoreID(getMvpView().getStoreDetails().getStoreId());
            confingReq.setTerminalID(getMvpView().getTerminalId());
            if (api != null) {
                Call<ConfingRes> call = api.CONFING_RES_CALL(confingReq);
                call.enqueue(new Callback<ConfingRes>() {
                    @Override
                    public void onResponse(@NotNull Call<ConfingRes> call, @NotNull Response<ConfingRes> response) {
                        if (response.isSuccessful()) {
                            getDataManager().setEposURL(getMvpView().getEposURL());
                            getDataManager().storeEposUrl(true);
                            getMvpView().hideLoading();
                            if (response.body() != null && response.body().getRequestStatus() == 0) {
                                handleStoreSetupService();
                            } else {
                                getMvpView().showMessage(response.body().getReturnMessage());
                            }
                        } else {
                            getMvpView().hideLoading();
                            getMvpView().showMessage("Unable to communicate");
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ConfingRes> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().hideLoading();
                getMvpView().showMessage("not a valid url");
            }
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onVerifyClick() {
        getMvpView().onVerifyClick();
    }
}
