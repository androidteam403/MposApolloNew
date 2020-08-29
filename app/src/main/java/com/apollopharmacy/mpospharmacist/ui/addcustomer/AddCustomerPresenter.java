package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorReqModel;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerPresenter<V extends AddCustomerMvpView> extends BasePresenter<V>
        implements AddCustomerMvpPresenter<V> {
    @Inject
     AddCustomerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDateClick() {
        getMvpView().onDateClick();
    }

    @Override
    public void onClickSubmit() {
        getMvpView().onSubmitClick();
    }

    @Override
    public void onClickAnniversary() {
        getMvpView().onAnniversaryClick();
    }

    @Override
    public void onClickRegistration() {
        getMvpView().onRegistrationClick();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void handleCustomerAddService() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            AddCustomerReqModel addCustomerReqModel = new AddCustomerReqModel();
            addCustomerReqModel.setFirstName(getMvpView().getFirstName());
            addCustomerReqModel.setMiddleName(getMvpView().getMiddleName());
            addCustomerReqModel.setLastName(getMvpView().getLastName());
            addCustomerReqModel.setPostalAddress(getMvpView().getPostalAddress());
            addCustomerReqModel.setCity(getMvpView().getCityOption());
            addCustomerReqModel.setState(getMvpView().getStateOption());
            addCustomerReqModel.setDistrict(getMvpView().getDistrictOption());
            addCustomerReqModel.setZipCode(getMvpView().getZipCode());
            addCustomerReqModel.setEmail(getMvpView().getEmail());
            addCustomerReqModel.setTelephone(getMvpView().getTelephone());
            addCustomerReqModel.setDataAreaId(getDataManager().getDataAreaId());
            addCustomerReqModel.setMobile(getMvpView().getMobile());
            addCustomerReqModel.setDOB(getMvpView().getDOB());
            addCustomerReqModel.setDOA("1900-01-01");
            addCustomerReqModel.setAge(getMvpView().getAge());
            addCustomerReqModel.setGender(getMvpView().getGenderOption());
            addCustomerReqModel.setMaritalStatus(getMvpView().getMaritalStatus());
            addCustomerReqModel.setDependentsNo(getMvpView().getNumberOfDependants());
            addCustomerReqModel.setCardNumber(getMvpView().getCardNumber());
            addCustomerReqModel.setRegistrationDate(getMvpView().getDateOfReg());
            addCustomerReqModel.setCorpId("");
            addCustomerReqModel.setCustId("");
            addCustomerReqModel.setStoreId(getDataManager().getStoreId());
            addCustomerReqModel.setAXDomain(getDataManager().getGlobalJson().getAXServiceDomain());
            addCustomerReqModel.setAXUserId(getDataManager().getGlobalJson().getAXServiceUsername());
            addCustomerReqModel.setAXPassword(getDataManager().getGlobalJson().getAXServicePassword());
            addCustomerReqModel.setCustomerCreationURL(getDataManager().getGlobalJson().getAXServiceURL());
            addCustomerReqModel.setRequestStatus(getDataManager().getGlobalJson().getRequestStatus());
            addCustomerReqModel.setReturnMessage(getDataManager().getGlobalJson().getReturnMessage());
            Call<AddCustomerResModel> call = api.ADD_CUSTOMER_SERVICE(addCustomerReqModel);
            call.enqueue(new Callback<AddCustomerResModel>() {
                @Override
                public void onResponse(@NotNull Call<AddCustomerResModel> call, @NotNull Response<AddCustomerResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().addCustomerSuccess(response.body());
                        } else {
                            getMvpView().addCustomerFailed(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddCustomerResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public String getStoreName() {
        return getDataManager().getGlobalJson().getStoreName();
    }

    @Override
    public String getStoreId() {
        return getDataManager().getStoreId();
    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

}
