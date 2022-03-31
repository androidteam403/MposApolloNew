package com.apollopharmacy.mpospharmacistTest.ui.splash;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.utils.MyAdmin;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void decideNextActivity() {
        if (getMvpView() != null) {
            if (getDataManager().isAdminLoginFinish()) {
                if (getDataManager().isAdminSetUpFinish())
                    if (getDataManager().isUserLogin())
                        getMvpView().openMainActivity();
                    else
                        getMvpView().openLoginActivity();
                else
                    getMvpView().storeSetupActivity();
            } else {
                getMvpView().openAdminSetupActivity();
            }
        }
    }


    @Override
    public void getGlobalConfigration() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<GetGlobalConfingRes> call = api.GET_GLOBAL_CONFING_RES_CALL(getDataManager().getStoreId(),getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<GetGlobalConfingRes>() {
                @Override
                public void onResponse(@NotNull Call<GetGlobalConfingRes> call, @NotNull Response<GetGlobalConfingRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            getDataManager().storeGlobalJson(json);
                            getMvpView().openMainActivity();
                        } else {
                            if (response.body() != null)
                                getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetGlobalConfingRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    //   getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void enableKioskMode() {
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getMvpView().getBaseActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mDPM = new ComponentName(getMvpView().getBaseActivity(), MyAdmin.class);
        if (myDevicePolicyManager != null) {

            myDevicePolicyManager.removeActiveAdmin(mDPM);
            if (myDevicePolicyManager.isDeviceOwnerApp(getMvpView().getBaseActivity().getPackageName())) {
                String[] packages = {getMvpView().getBaseActivity().getPackageName()};
                myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
            } else {
                getDataManager().setKioskMode(true);
                getMvpView().startAnimation();
            }
        }

    }
}
