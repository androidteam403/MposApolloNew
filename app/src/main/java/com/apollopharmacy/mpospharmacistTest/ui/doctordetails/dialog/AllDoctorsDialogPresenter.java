package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDoctorsDialogPresenter<V extends AllDoctorsDialogMvpView> extends BasePresenter<V>
        implements AllDoctorsDialogMvpPresenter<V> {

    @Inject
    public AllDoctorsDialogPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void dismissDialog() {
        getMvpView().dismissDialog();
    }

    @Override
    public void onAddDoctorClick() {
        getMvpView().onAddDoctorClick();
    }

    @Override
    public void searchDoctorDetailsByName() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("");
            doctorSearchModel.setDoctorName(getMvpView().doctorSearch());
            doctorSearchModel.setClusterId(getDataManager().getGlobalJson().getClusterCode());
            doctorSearchModel.setDoctorBaseUrl(getDataManager().getGlobalJson().getDoctorSearchUrl());
            Call<DoctorSearchResModel> call = api.getDoctorsList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().onSuccessDoctorSearch(response.body());
                    } else {
                        getMvpView().onFailureDoctorSerach();
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
    public void defaultDoctorList() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("0");
            doctorSearchModel.setDoctorName("");
            doctorSearchModel.setClusterId(getDataManager().getGlobalJson().getClusterCode());
            doctorSearchModel.setDoctorBaseUrl(getDataManager().getGlobalJson().getDoctorSearchUrl());
            Call<DoctorSearchResModel> call = api.getDoctorsList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().onSuccessDefaultDoctorSearch(response.body());
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
}