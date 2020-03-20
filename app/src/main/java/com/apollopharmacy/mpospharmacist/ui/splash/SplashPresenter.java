package com.apollopharmacy.mpospharmacist.ui.splash;

import android.os.Handler;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

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
        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(this::decideNextActivity, SPLASH_DISPLAY_LENGTH);
    }

    private void decideNextActivity() {
        if (getDataManager().isAdminLoginFinish()) {
            if (getDataManager().isAdminSetUpFinish())
                if (getDataManager().isUserLogin())
                    getTenderTypeApi();
                else
                    getMvpView().openLoginActivity();
            else
                getMvpView().storeSetupActivity();
        } else {
            getMvpView().openAdminSetupActivity();
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
                            getMvpView().openMainActivity();
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
                    getMvpView().showMessage(R.string.some_error);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
