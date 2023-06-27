package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetPrDetailsApiRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetPrDetailsApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetUniversalDropDownBindRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetUniversalDropDownBindResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.PrsInventTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.PrsInvntTransactionDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockInwardProcessDetailsPresenter<V extends StockInwardProcessDetailsMvpView> extends BasePresenter<V>
        implements StockInwardProcessDetailsMvpPresenter<V> {

    @Inject
    public StockInwardProcessDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void getUniversalDropDown() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetUniversalDropDownBindRequest reqModel = new GetUniversalDropDownBindRequest();
            reqModel.setStoreId("16001");
            reqModel.setType("GETUNIVERSALDATA");
            reqModel.setValue("PRSREMARKS");


            Call<GetUniversalDropDownBindResponse> call = apiInterface.GET_UNIVERSAL_DROP_DOWN_BIND_API_CALL(reqModel);
            call.enqueue(new Callback<GetUniversalDropDownBindResponse>() {
                @Override
                public void onResponse(Call<GetUniversalDropDownBindResponse> call, Response<GetUniversalDropDownBindResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        getMvpView().onSuccessUniversalDropDownDetails(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GetUniversalDropDownBindResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void getPrDetailsApi(String referenceId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetPrDetailsApiRequest reqModel = new GetPrDetailsApiRequest();
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            reqModel.setPONumber(referenceId);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setData(null);
            reqModel.setErrorMessage(null);
            reqModel.setMessage(null);
            reqModel.setStatus(false);



            Call<GetPrDetailsApiResponse> call = apiInterface.GetPRSDetailsAPI(reqModel);
            call.enqueue(new Callback<GetPrDetailsApiResponse>() {
                @Override
                public void onResponse(Call<GetPrDetailsApiResponse> call, Response<GetPrDetailsApiResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        getMvpView().onSuccessPrDetails(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GetPrDetailsApiResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
