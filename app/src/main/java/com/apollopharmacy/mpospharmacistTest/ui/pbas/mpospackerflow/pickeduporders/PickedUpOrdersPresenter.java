package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickedUpOrdersPresenter<V extends PickedUpOrdersMvpView> extends BasePresenter<V>
        implements PickedUpOrdersMvpPresenter<V> {

    @Inject
    public PickedUpOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void startPickUp() {
        getMvpView().startPickUp();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }

    @Override
    public void fetchOMSOrderList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            OMSTransactionRequest reqModel = new OMSTransactionRequest();
            reqModel.setTransactionID("");
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<OMSTransactionResponse> call = api.GET_OMS_TRANSACTION_HEADER(reqModel);
            call.enqueue(new Callback<OMSTransactionResponse>() {
                @Override
                public void onResponse(Call<OMSTransactionResponse> call, Response<OMSTransactionResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null)
                        getMvpView().onSuccessGetOMSTransactionList(response.body());
                }


                @Override
                public void onFailure(Call<OMSTransactionResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public List<RacksDataResponse.FullfillmentDetail> getFullFillmentList() {
        return getDataManager().getFullFillmentList();
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList() {
        return getDataManager().getfullFillListOfListFiltered();
    }

    @Override
    public void setFullFillmentDataList(List<RacksDataResponse.FullfillmentDetail> fullFillmentDataList) {
        getDataManager().setFullFillmentList(fullFillmentDataList);
    }

    @Override
    public void setListOfListFullFillProducts(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullFillProducts) {
        getDataManager().setfullFillListOfListFiltered(listOfListFullFillProducts);
    }

}
