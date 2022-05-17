package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;


import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpVerificationPresenter<V extends PickUpVerificationMvpView> extends BasePresenter<V>
        implements PickUpVerificationMvpPresenter<V> {

    @Inject
    public PickUpVerificationPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onPartialWarningYesClick() {
        getMvpView().onPartialWarningYesClick();
    }

    @Override
    public void onPartialWarningNoClick() {
        getMvpView().onPartialWarningNoClick();
    }

    @Override
    public void onClickReVerificatio() {
        getMvpView().onClickReVerificatio();
    }

    @Override
    public void fetchOMSMedicineInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            MedicineBatchReqBean reqModel = new MedicineBatchReqBean();
            reqModel.setTransactionID(refNumber);
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<MedicineBatchResBean> call = api.GET_OMS_PHYSICAL_BATCH(reqModel);
            call.enqueue(new Callback<MedicineBatchResBean>() {
                @Override
                public void onResponse(@NotNull Call<MedicineBatchResBean> call, @NotNull Response<MedicineBatchResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().onSuccessGetOMSPhysicalBatch(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MedicineBatchResBean> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    // handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void fetchOMSCustomerInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CustomerDataReqBean reqModel = new CustomerDataReqBean();
            reqModel.setTransactionID(refNumber);
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<ArrayList<CustomerDataResBean>> call = api.GET_OMS_TRANSACTION(reqModel);
            call.enqueue(new Callback<ArrayList<CustomerDataResBean>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Response<ArrayList<CustomerDataResBean>> response) {
                    if (response.isSuccessful() && response.body() != null) {
//                        fetchOMSMedicineInfo(refNumber);
                        //System.out.println("Customer Name-->0"+response.body().get(0).getCustomerName());
                        getMvpView().onSuccessGetOMSTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    System.out.println("Customer Name-->4" + "Syntax Error");

                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }


    @Override
    public void onClickVerification() {
        getMvpView().onClickVerification();
    }

}
