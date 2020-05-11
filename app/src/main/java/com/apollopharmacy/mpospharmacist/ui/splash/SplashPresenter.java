package com.apollopharmacy.mpospharmacist.ui.splash;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorValidationReq;
import com.apollopharmacy.mpospharmacist.root.AppConstant;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.MyAdmin;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {
    private static final long SPLASH_DISPLAY_LENGTH = 2000;

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
        if(getMvpView() != null) {
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


    public void getTenderTypeApi() {
        if (getMvpView().isNetworkConnected()) {
         //   getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();

            Call<GetTenderTypeRes> call = api.GET_TENDER_TYPE_RES_CALL(getDataManager().getStoreId(),getDataManager().getDataAreaId(),new Object());
            call.enqueue(new Callback<GetTenderTypeRes>() {
                @Override
                public void onResponse(@NotNull Call<GetTenderTypeRes> call, @NotNull Response<GetTenderTypeRes> response) {
                    if (response.isSuccessful()) {
               //         getMvpView().hideLoading();
                        if (response.body() != null && response.body().getGetTenderTypeResult() != null && response.body().getGetTenderTypeResult().getRequestStatus() == 0) {
                           Singletone.getInstance().tenderTypeResultEntity = response.body().getGetTenderTypeResult();
                            getGlobalConfigration();
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getGetTenderTypeResult().getReturnMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetTenderTypeRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    handleApiError(t);
                  //  getMvpView().showMessage(R.string.some_error);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void checkValidateVendor() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface

            ApiInterface api = ApiClient.getApiService();
            VendorValidationReq validationReq = new VendorValidationReq();
            validationReq.setDEVICEID("");
            validationReq.setKEY("");
            Call<VendorCheckRes> call = api.VENDOR_CHECK_RES_CALL("", validationReq);
            call.enqueue(new Callback<VendorCheckRes>() {
                @Override
                public void onResponse(@NotNull Call<VendorCheckRes> call, @NotNull Response<VendorCheckRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatus()) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            getDataManager().setVendorRes(json);
                            if (getDataManager().isUserLogin()) {

                            } else {

                                getMvpView().openLoginActivity();
                            }
                        } else {

                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<VendorCheckRes> call, @NotNull Throwable t) {

                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getGlobalConfigration() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
          //  getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            Call<GetGlobalConfingRes> call = api.GET_GLOBAL_CONFING_RES_CALL(getDataManager().getStoreId(),getDataManager().getDataAreaId(),new Object());
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
                            if(response.body() != null)
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
        // get policy manager
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getMvpView().getBaseActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        // get this app package name
        ComponentName mDPM = new ComponentName(getMvpView().getBaseActivity(), MyAdmin.class);

        //startLockTask();
        if (myDevicePolicyManager != null) {

            myDevicePolicyManager.removeActiveAdmin(mDPM);
//            myDevicePolicyManager.clearDeviceOwnerApp(getMvpView().getBaseActivity().getPackageName());
            if (myDevicePolicyManager.isDeviceOwnerApp(getMvpView().getBaseActivity().getPackageName())) {
                // get this app package name
                String[] packages = {getMvpView().getBaseActivity().getPackageName()};
                // mDPM is the admin package, and allow the specified packages to lock task
                myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
            } else {
               // getMvpView().showMessage("Not owner");
                getDataManager().setKioskMode(true);
                getMvpView().startAnimation();
                //startLockTask();
            }
//            if (myDevicePolicyManager.isLockTaskPermitted(getMvpView().getBaseActivity().getPackageName())) {
//                getDataManager().setKioskMode(true);
//                getMvpView().startAnimation();
//            } else {
//                getMvpView().displayKioskRequiredDialog();
//            }

        }

    }
}
