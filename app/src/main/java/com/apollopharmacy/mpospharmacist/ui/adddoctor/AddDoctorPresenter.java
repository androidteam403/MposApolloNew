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
            ApiInterface api = ApiClient.getApiService();
            AddDoctorReqModel doctorReqModel = new AddDoctorReqModel();
            doctorReqModel.setDocRegID(getMvpView().getDoctorRegNo());
            doctorReqModel.setDocAXID("");
            doctorReqModel.setDocName(getMvpView().getDoctorName());
            doctorReqModel.setSpeciality(getMvpView().getSpeciality());
            doctorReqModel.setPracticePlace(getMvpView().getPlaceOfPractice());
            doctorReqModel.setAddress(getMvpView().getAddress());
            doctorReqModel.setState("");
            doctorReqModel.setPhoneNo(getMvpView().getPhoneNo());
            doctorReqModel.setStoreId("16001");
            doctorReqModel.setRECID("");
            doctorReqModel.setDataAreaID("AHEL");
            doctorReqModel.setAXDomain("apollopharmacy.org");
            doctorReqModel.setAXUserId("ap36695");
            doctorReqModel.setAXPassword("Pharmacyax@123");
            doctorReqModel.setDoctorCreationURL("net.tcp://172.16.1.179:8201/DynamicsAx/Services/");
            doctorReqModel.setClusterCode("14907");
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
//        if (getMvpView().isNetworkConnected()){
//            AddDoctorModel addDoctorModel = new AddDoctorModel();
//            addDoctorModel.setDoctorRegNo(getMvpView().getDoctorRegNo());
//            addDoctorModel.setDoctorName(getMvpView().getDoctorName());
//            addDoctorModel.setSpeciality(getMvpView().getSpeciality());
//            addDoctorModel.setPlaceOfPractice(getMvpView().getPlaceOfPractice());
//            addDoctorModel.setAddress(getMvpView().getAddress());
//            addDoctorModel.setPhoneNumber(getMvpView().getPhoneNo());
//        }
    }
}
