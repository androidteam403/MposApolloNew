package com.apollopharmacy.mpospharmacistTest.ui.home;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter<V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityMvpPresenter<V> {
    @Inject
    public MainActivityPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName();
//        return getDataManager().getUserName() + "\n" + getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {

       return getDataManager().getGlobalJson().getStoreName() + "\n" + getDataManager().getStoreId();
    }

    @Override
    public void logoutUser() {
        getDataManager().logoutUser();
        getMvpView().navigateLoginActivity();
    }

    @Override
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().getCorporateList(response.body());
                        getUnpostedTransaction();
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getUnpostedTransaction() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.GET_UNPOSTED_TRANSACTION(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if (response.body().getSalesLine() != null && response.body().getSalesLine().size() > 0)
                                getMvpView().onSuccessGetUnPostedPOSTransaction(response.body());
                        } else
                            getMvpView().hideLoading();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
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
    public boolean isKisokMode() {
        return getDataManager().isKioskMode();
    }

    @Override
    public boolean isOpenScreens() {
        return getDataManager().isOpenScreens();
    }

    @Override
    public void enableopenscreens() {
        getDataManager().setOpenScreens(true);

    }

    @Override
    public void disablescreens() {
        getDataManager().setOpenScreens(false);

    }

    @Override
    public void getGlobalConfig() {
        getMvpView().getGlobalConfig(getDataManager().getGlobalJson());
    }


}
