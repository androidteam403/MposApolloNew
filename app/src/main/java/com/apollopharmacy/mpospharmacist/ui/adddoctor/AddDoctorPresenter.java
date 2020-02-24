package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorReqModel;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDoctorPresenter<V extends AddDoctorMvpView> extends BasePresenter<V>
        implements AddDoctorMvpPresenter<V> {
    @Inject
    public AddDoctorPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSubmitBtnClick() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            AddDoctorReqModel doctorReqModel = new AddDoctorReqModel();
            doctorReqModel.setDocRegID("");
            doctorReqModel.setDocAXID("");
            doctorReqModel.setDocName("");
            doctorReqModel.setSpeciality("");
            doctorReqModel.setPracticePlace("");
            doctorReqModel.setAddress("");
            doctorReqModel.setState("");
            doctorReqModel.setPhoneNo("");
            doctorReqModel.setStoreId("");
            doctorReqModel.setRECID("");
            doctorReqModel.setDataAreaID("");
            doctorReqModel.setAXDomain("");
            doctorReqModel.setAXUserId("");
            doctorReqModel.setAXPassword("");
            doctorReqModel.setDoctorCreationURL("");
            doctorReqModel.setClusterCode("");
            doctorReqModel.setRequestStatus(1);
            doctorReqModel.setReturnMessage("");
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
