package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsReq;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListPresenter<V extends ProductListMvpView> extends BasePresenter<V>
        implements ProductListMvpPresenter<V> {
    private Call<GetItemDetailsRes> call;
    @Inject
    public ProductListPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBackPress() {
        getMvpView().onClickBackBtn();
    }

    @Override
    public void getProductDetails() {
        if (!TextUtils.isEmpty(getMvpView().getSearchProductKey())) {
            if (getMvpView().isNetworkConnected()) {
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService();
                GetItemDetailsReq customerRequest = new GetItemDetailsReq();
                customerRequest.setSearchString(getMvpView().getSearchProductKey());
                customerRequest.setCorpCode(getMvpView().getCorporateValue().getCode());
                customerRequest.setStoreID(getDataManager().getStoreId());
                customerRequest.setStockCheck(false); // To check manual billing !Singletone.getInstance().isManualBilling
                if(call != null){
                    call.cancel();
                }

                call = api.GET_ITEM_DETAILS_RES_CALL(customerRequest);
                call.enqueue(new Callback<GetItemDetailsRes>() {
                    @Override
                    public void onResponse(@NotNull Call<GetItemDetailsRes> call, @NotNull Response<GetItemDetailsRes> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            getMvpView().hideLoading();
                            if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0)
                                getMvpView().onSuccessGetItems(response.body());
                            else
                                getMvpView().onFailedGetItems(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetItemDetailsRes> call, @NotNull Throwable t) {
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
    public void onBarCodeClick() {
        getMvpView().onBarCodeClick();
    }
}
