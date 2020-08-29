package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorReqModel;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddDoctorBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDoctorPresenter<V extends AddDoctorMvpView> extends BasePresenter<V>
        implements AddDoctorMvpPresenter<V> {
    ActivityAddDoctorBinding addDoctorBinding;

    @Inject
    public AddDoctorPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSubmitBtnClick() {
        getMvpView().onSubmitBtnClick();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void handleAddDoctorService() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            AddDoctorReqModel doctorReqModel = new AddDoctorReqModel();
            doctorReqModel.setDocRegID(getMvpView().getDoctorRegNo());
            doctorReqModel.setDocAXID("");
            doctorReqModel.setDocName(getMvpView().getDoctorName());
            doctorReqModel.setSpeciality(getMvpView().getSpeciality());
            doctorReqModel.setPracticePlace(getMvpView().getPlaceOfPractice());
            doctorReqModel.setAddress(getMvpView().getAddress());
            doctorReqModel.setState("");
            doctorReqModel.setPhoneNo(getMvpView().getPhoneNo());
            doctorReqModel.setStoreId(getDataManager().getStoreId());
            doctorReqModel.setRECID("");
            doctorReqModel.setDataAreaID("AHEL");
            doctorReqModel.setAXDomain(getDataManager().getGlobalJson().getAXServiceDomain());
            doctorReqModel.setAXUserId(getDataManager().getGlobalJson().getAXServiceUsername());
            doctorReqModel.setAXPassword(getDataManager().getGlobalJson().getAXServicePassword());
            doctorReqModel.setDoctorCreationURL(getDataManager().getGlobalJson().getAXServiceURL());
            doctorReqModel.setClusterCode(getDataManager().getGlobalJson().getClusterCode());
            doctorReqModel.setRequestStatus(getDataManager().getGlobalJson().getRequestStatus());
            doctorReqModel.setReturnMessage(getDataManager().getGlobalJson().getReturnMessage());
            Call<AddDoctorResModel> call = api.ADD_DOCTOR_SERVICE(doctorReqModel);
            call.enqueue(new Callback<AddDoctorResModel>() {
                @Override
                public void onResponse(@NotNull Call<AddDoctorResModel> call, @NotNull Response<AddDoctorResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().addDoctorSuccess(response.body());
                        } else {
                            getMvpView().addDoctorFailed(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddDoctorResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
