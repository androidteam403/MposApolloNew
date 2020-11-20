package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import android.util.Log;
import android.util.Pair;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewAdminLoginPresenter <V extends NewAdminLoginMvpView> extends BasePresenter<V>
        implements NewAdminLoginMvpPresenter<V> {
    @Inject
    public NewAdminLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAdminLoginClick() {
        if(getMvpView().validation()) {
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
                            if (response.body()!= null && response.body().isStatus()) {
                                getDataManager().setAdminLoginId(getMvpView().getUserID());
                                getDataManager().setAdminLoginFinish(true);
                                getMvpView().userLoginSuccess(response.body());
                            } else {
                                if(response.body()!= null)
                                    getMvpView().userLoginFailed(response.body().getMessage());
                                else
                                    handleApiError(response.code());
                            }
                        }else{
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
