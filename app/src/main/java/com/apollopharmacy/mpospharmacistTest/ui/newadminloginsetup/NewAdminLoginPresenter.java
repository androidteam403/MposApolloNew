package com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model.AdminLoginReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAdminLoginPresenter<V extends NewAdminLoginMvpView> extends BasePresenter<V>
        implements NewAdminLoginMvpPresenter<V> {
    @Inject
    public NewAdminLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAdminLoginClick() {
        if (getMvpView().validation()) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                ApiInterface api = ApiClient.getApiService2();
                AdminLoginReqModel loginReqModel = new AdminLoginReqModel();
                loginReqModel.setUserId(getMvpView().getUserID());
                loginReqModel.setPassword(getMvpView().getPassword());
                loginReqModel.setUserType("ADMIN");
                Call<AdminLoginResModel> call = api.LOGIN_SERVICE_CALL(loginReqModel);
                call.enqueue(new Callback<AdminLoginResModel>() {
                    @Override
                    public void onResponse(@NotNull Call<AdminLoginResModel> call, @NotNull Response<AdminLoginResModel> response) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().isStatus()) {
                                getDataManager().setAdminLoginId(getMvpView().getUserID());
                                getDataManager().setAdminLoginFinish(true);
                                getMvpView().userLoginSuccess(response.body());
                            } else {
                                if (response.body() != null)
                                    getMvpView().userLoginFailed(response.body().getMessage());
                                else
                                    handleApiError(response.code());
                            }
                        } else {
                            handleApiError(response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AdminLoginResModel> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

}
