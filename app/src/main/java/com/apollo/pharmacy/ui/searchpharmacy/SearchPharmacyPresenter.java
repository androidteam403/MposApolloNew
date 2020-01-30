package com.apollo.pharmacy.ui.searchpharmacy;

import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.data.network.ApiClient;
import com.apollo.pharmacy.data.network.ApiInterface;
import com.apollo.pharmacy.ui.base.BasePresenter;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPharmacyPresenter<V extends SearchPharmacyMvpView> extends BasePresenter<V>
        implements SearchPharmacyMvpPresenter<V> {
    @Inject
    public SearchPharmacyPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void finishActivity() {
        getMvpView().activityFinish();
    }

    @Override
    public void getPharmacyList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();
            Call<Pharmacy> call = api.getPharmacyList();
            call.enqueue(new Callback<Pharmacy>() {
                @Override
                public void onResponse(Call<Pharmacy> call, Response<Pharmacy> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().onSuccessGetPharmacy(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Pharmacy> call, Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onPharmacyItemClick(Pharmacy.StoreListObj model) {
        getMvpView().onItemClick(model);
    }

    @Override
    public void onSetUpClick() {
        getMvpView().onSetUpClick();
    }

    @Override
    public void insertAdminLoginDetails() {
        getDataManager().setAdminSetUpFinish(true);
        getMvpView().onNavigateHomeScreen();
    }
}
