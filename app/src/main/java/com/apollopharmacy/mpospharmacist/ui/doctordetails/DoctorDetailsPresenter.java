package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
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
    public void getDoctorsList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("");
            doctorSearchModel.setDoctorName("");
            doctorSearchModel.setClusterId("14907");
            doctorSearchModel.setDoctorBaseUrl("");
            Call<DoctorSearchResModel> call = api.getDoctorsList(doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful())
                            getMvpView().getDoctorSearchList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DoctorSearchResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
