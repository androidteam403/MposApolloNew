package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacistLoginPresenter<V extends PharmacistLoginMvpView> extends BasePresenter<V>
        implements PharmacistLoginMvpPresenter<V> {
    @Inject
    public PharmacistLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickLogin() {
        getMvpView().onClickLogin();
    }

    @Override
    public void onInstoreCLick() {
        getMvpView().onClickInstore();
    }

    @Override
    public void onSelectCampaign() {
        getMvpView().onCampaignSelect();
    }

    @Override
    public void getUserId() {
        if (getMvpView().isNetworkConnected()) {


            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<UserModel> call = api.getUserIds(new JsonObject());

            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().getUserIds(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");

        }
    }

    @Override
    public void getCampaign() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<UserModel> call = api.getUserIds(new JsonObject());
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().getUserIds(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void userLoginApi() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            LoginReqModel loginReqModel = new LoginReqModel();
            loginReqModel.setUserID(getMvpView().getUserId());
            loginReqModel.setStoreID(getMvpView().getStoreId());
            loginReqModel.setTerminalID(getMvpView().getTerminalId());
            loginReqModel.setPassword(getMvpView().getUserPassword());
            Call<LoginResModel> call = api.LOGIN_RES_MODEL_CALL(loginReqModel);
            call.enqueue(new Callback<LoginResModel>() {
                @Override
                public void onResponse(@NotNull Call<LoginResModel> call, @NotNull Response<LoginResModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful())
                            getMvpView().userLoginSuccess(response.body());
                        else
                            getMvpView().userLoginFailed();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResModel> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
