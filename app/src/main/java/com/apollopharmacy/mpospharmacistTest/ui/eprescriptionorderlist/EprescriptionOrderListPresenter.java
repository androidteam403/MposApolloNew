package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model.OMSTransactionHeaderReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EprescriptionOrderListPresenter<V extends EprescriptionOrderListMvpView> extends BasePresenter<V>
    implements EprescriptionOrderListMvpPresenter<V>
{

    @Inject
    EprescriptionOrderListPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable)
    {
            super(manager, schedulerProvider, compositeDisposable);
        }

    @Override
    public void onActionBarBackPressed()
    {
        getMvpView().onClickBackPressed();
    }

    @Override
    public  void onClickPickingbutton()
    {
        getMvpView().PickingOrderPressed();
    }

    @Override
    public  void onClickPackingbutton()
    {
        getMvpView().PackingOrderPressed();

    }


    @Override
    public void onClickInvoicebutton()
    {
        getMvpView().InvoiceOrderPressed();
    }


    @Override
    public void fetchOMSOrderList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            OMSTransactionHeaderReqModel reqModel = new OMSTransactionHeaderReqModel();
            reqModel.setTransactionID("");
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
           /* Call<OMSTransactionHeaderResModel> call = api.GET_OMS_TRANSACTION_HEADER(reqModel);
            call.enqueue(new Callback<OMSTransactionHeaderResModel>() {
                @Override
                public void onResponse(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Response<OMSTransactionHeaderResModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null)
                        getMvpView().onSuccessGetOMSTransactionList(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });*/
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

}
