package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerRequest;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsPresenter<V extends CustomerDetailsMvpView> extends BasePresenter<V>
        implements CustomerDetailsMvpPresenter<V> {
    @Inject
    public CustomerDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAddCustomerClick() {
        getMvpView().onAddCustomerClick();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void onCustomerSearchClick() {
        if(!TextUtils.isEmpty(getMvpView().getCustomerNumber())){
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService();
                GetCustomerRequest customerRequest = new GetCustomerRequest();
                customerRequest.setSearchString(getMvpView().getCustomerNumber());

                Call<GetCustomerResponse> call = api.GET_CUSTOMER_REQUEST_CALL(customerRequest);
                call.enqueue(new Callback<GetCustomerResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetCustomerResponse> call, @NotNull Response<GetCustomerResponse> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                            if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0)
                                getMvpView().onSuccessCustomerSearch(response.body());
                            else
                                getMvpView().onFailedCustomerSearch();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetCustomerResponse> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

    @Override
    public void onVoiceSearchClick() {
        getMvpView().onVoiceSearchClick();
    }

    @Override
    public void onClickSelectBtn(GetCustomerResponse.CustomerEntity customerEntity) {
        getMvpView().onSubmitBtnClick(customerEntity);
    }

    @Override
    public void onClickEditBtn(GetCustomerResponse.CustomerEntity customerEntity) {
        getMvpView().onEditBtnClick(customerEntity);
    }


}
