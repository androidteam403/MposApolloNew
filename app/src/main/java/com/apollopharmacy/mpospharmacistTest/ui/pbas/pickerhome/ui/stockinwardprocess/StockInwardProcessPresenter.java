package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockInwardProcessPresenter<V extends StockInwardProcessMvpView> extends BasePresenter<V>
        implements StockInwardProcessMvpPresenter<V> {

    @Inject
    public StockInwardProcessPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickFromDate() {
        getMvpView().onClickFromDate();
    }

    @Override
    public void onClickToDate() {
        getMvpView().onClickToDate();
    }

    @Override
    public void onClickShow() {
        getMvpView().onClickShow();
    }

    @Override
    public void getInventoryTransactionDetails(String fromDate, String toDate) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetInventoryTransactionDetailsRequest reqModel = new GetInventoryTransactionDetailsRequest();
            reqModel.setFromDate(fromDate);
            reqModel.setToDate(toDate);
            reqModel.setRefNo("");
            reqModel.setSite("");
            reqModel.setISUnposted(false);
            Call<GetInventoryTransactionDetailsResponse> call = apiInterface.GET_INVENTORY_TRANSACTION_DETAILS_RESPONSE_CALL(reqModel);
            call.enqueue(new Callback<GetInventoryTransactionDetailsResponse>() {
                @Override
                public void onResponse(Call<GetInventoryTransactionDetailsResponse> call, Response<GetInventoryTransactionDetailsResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        getMvpView().onSuccessgetInventoryTransactionDetails(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GetInventoryTransactionDetailsResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
