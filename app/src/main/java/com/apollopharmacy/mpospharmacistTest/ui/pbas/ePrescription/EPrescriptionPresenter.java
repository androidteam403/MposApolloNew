package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EPrescriptionPresenter  <V extends EPrescriptionMvpView> extends BasePresenter<V>
        implements EPrescriptionMvpPresenter<V> {

    @Inject
    public EPrescriptionPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchFulfilmentOrderList() {
        if (getMvpView().isNetworkConnected()) {
           getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            EPrescriptionModelClassRequest reqModel = new EPrescriptionModelClassRequest();
            reqModel.setPosExpiry(0);
            reqModel.setStoreId("16001");
            reqModel.setTrasactionId("");
            reqModel.setCancelReasion("");
            reqModel.setPrescriptionNo("17000000134");
            reqModel.setStatusId("");
            reqModel.setDataSourceType("");
            reqModel.setOperationType("LOADPAGE");
            Call<List<EPrescriptionModelClassResponse>> call = apiInterface.GET_ONLINE_ORDER_TRANSACTION(reqModel);


                call.enqueue(new Callback<List<EPrescriptionModelClassResponse>>() {
                    @Override
                    public void onResponse(Call<List<EPrescriptionModelClassResponse>> call, Response<List<EPrescriptionModelClassResponse>> response) {
                        getMvpView().hideLoading();
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                getMvpView().onSucessPrescriptionList(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EPrescriptionModelClassResponse>> call, Throwable t) {
                        getMvpView().hideLoading();
                        handleApiError(t);
                    }
                });
        }
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName() + "\n" + getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {
//        return getDataManager().getGlobalJson().getStoreName() + "\n" + getDataManager().getStoreId();

        return getDataManager().getGlobalJson().getStoreName();
    }

    @Override
    public String getTerminalId() {
//        return getDataManager().getGlobalJson().getStoreName() + "\n" + getDataManager().getStoreId();

        return getDataManager().getTerminalId();
    }
}
