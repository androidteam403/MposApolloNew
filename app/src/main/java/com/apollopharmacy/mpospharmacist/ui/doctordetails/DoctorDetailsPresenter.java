package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialog;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailsPresenter<V extends DoctorDetailsMvpView> extends BasePresenter<V>
        implements DoctorDetailsMvpPresenter<V> {

    @Inject
    public DoctorDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAddDoctorClick() {
        getMvpView().onAddDoctorClick();
    }

    @Override
    public void onBackPressedClick() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void onAllDoctorsClick() {
        getMvpView().onAllDoctorsClick();
    }

    @Override
    public void getDoctorsList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("");
            doctorSearchModel.setDoctorName("");
            doctorSearchModel.setClusterId(getDataManager().getGlobalJson().getClusterCode());
            doctorSearchModel.setDoctorBaseUrl(getDataManager().getGlobalJson().getDoctorSearchUrl());
            Call<DoctorSearchResModel> call = api.getDoctorsList(getDataManager().getStoreId(),getDataManager().getDataAreaId(),doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful()) {
                        getSalesOrigin();
                        getMvpView().getDoctorSearchList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DoctorSearchResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getSalesOrigin() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService();
            Call<SalesOriginResModel> call = api.getSalesOriginList(getDataManager().getDataAreaId(),new JsonObject());
            call.enqueue(new Callback<SalesOriginResModel>() {
                @Override
                public void onResponse(@NotNull Call<SalesOriginResModel> call, @NotNull Response<SalesOriginResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        getMvpView().getSalesOriginList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SalesOriginResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getAllDoctorsList() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService();
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("0");
            doctorSearchModel.setDoctorName("");
            doctorSearchModel.setClusterId(getDataManager().getGlobalJson().getClusterCode());
            doctorSearchModel.setDoctorBaseUrl(getDataManager().getGlobalJson().getDoctorSearchUrl());
            Call<DoctorSearchResModel> call = api.getDoctorsList(getDataManager().getStoreId(),getDataManager().getDataAreaId(),doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().getAllDoctorsSearchList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DoctorSearchResModel> call, @NotNull Throwable t) {
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onSubmitClick() {
        getMvpView().onSubmitClick();
    }

    @Override
    public void onCustomDoctorLayoutClick() {
        getMvpView().onCustomDoctorLayoutClick();
    }
}
