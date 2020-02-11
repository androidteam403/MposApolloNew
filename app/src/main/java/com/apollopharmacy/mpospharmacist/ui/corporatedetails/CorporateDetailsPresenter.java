package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CorporateDetailsPresenter<V extends CorporateDetailsMvpView> extends BasePresenter<V>
        implements CorporateDetailsMvpPresenter<V> {
    @Inject
    public CorporateDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            Call<CorporateModel> call = api.getCorporateList(new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        getMvpView().getCorporateList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    getMvpView().showNotFoundCorporate();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }
}
