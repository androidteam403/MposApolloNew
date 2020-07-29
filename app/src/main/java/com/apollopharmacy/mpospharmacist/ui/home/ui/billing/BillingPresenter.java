package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingPresenter<V extends BillingMvpView> extends BasePresenter<V>
        implements BillingMvpPresenter<V> {

    @Inject
    public BillingPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCustomerSearchClick() {
        getMvpView().onCustomerSearchClick();
    }

    @Override
    public void onDoctorSearchClick() {
        getMvpView().onDoctorSearchClick();
    }

    @Override
    public void onCorporateSearchClick() {
        getMvpView().onCorporateSearchClick();
    }

    @Override
    public void onActionBarBackPress() {
        getMvpView().onBackPressedClick();
    }

    @Override
    public void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity) {
        getMvpView().customerEditClick(customerEntity);
    }

    @Override
    public void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        getMvpView().onDoctorEditClick(doctorEntity, salesEntity);
    }

    @Override
    public void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity) {
        getMvpView().onCorporateEditClick(corporateEntity);
    }

    @Override
    public void onContinueBtnClick() {
        getMvpView().onContinueBtnClick();
    }

    @Override
    public void getTransactionID() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            TransactionIDReqModel transactionIDModel = new TransactionIDReqModel();
            transactionIDModel.setRequestStatus(0);
            transactionIDModel.setReturnMessage("");
            transactionIDModel.setResultValue("");
            transactionIDModel.setTransactionID("");
            transactionIDModel.setStoreID(getDataManager().getGlobalJson().getStoreID());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getDataManager().getGlobalJson().getDataAreaID());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {

                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().showTransactionID(response.body());
                        getCorporateList();
                    }else{
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }else{
                            getMvpView().showMessage(R.string.some_error);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TransactionIDResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            //getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(),getDataManager().getDataAreaId(),new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().getCorporateList(response.body());
                        getDoctorsList();
                    }else{
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
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
                    if (response.isSuccessful()  && response.body() != null && response.body().getRequestStatus() == 0) {
                        getSalesOrigin();
                        getMvpView().getDoctorSearchList(response.body());
                    }else{
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
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
                    if (response.isSuccessful()  && response.body() != null && response.body().getGetSalesOriginResult().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().getSalesOriginList(response.body());
                    }else{
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getGetSalesOriginResult().getRequestStatus());
                        }
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


}
