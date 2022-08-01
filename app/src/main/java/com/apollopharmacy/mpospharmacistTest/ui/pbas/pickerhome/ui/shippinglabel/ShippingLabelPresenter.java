package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingLabelPresenter<V extends ShippingLabelMvpView> extends BasePresenter<V>
        implements ShippingLabelMvpPresenter<V> {
    @Inject
    public ShippingLabelPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getJounalTransactionApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            OrderListReq orderListReq = new OrderListReq();
            orderListReq.setArtName("");
            orderListReq.setBatchNo("");
            orderListReq.setCardNo("");
            orderListReq.setCustomerAccount("");
            orderListReq.setCustomerName("");
            orderListReq.setFromDate(CommonUtils.getCurrentDate("dd-MMM-yyyy")); // "01-Aug-2022"
            orderListReq.setHomeDelivery(false);
            orderListReq.setIPNumber("");
            orderListReq.setItemID("");
            orderListReq.setMobileNo("");
            orderListReq.setPendingBills(false);
            orderListReq.setPreviousBills(false);
            orderListReq.setReceiptId("");
            orderListReq.setToDate(CommonUtils.getCurrentDate("dd-MMM-yyyy")); //  "01-Aug-2022"


            Call<ArrayList<CalculatePosTransactionRes>> call = api.ORDER_LIST_RES_CALL(orderListReq);
            call.enqueue(new Callback<ArrayList<CalculatePosTransactionRes>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CalculatePosTransactionRes>> call, @NotNull Response<ArrayList<CalculatePosTransactionRes>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        getMvpView().onSuccessJounalTransactonApi(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<CalculatePosTransactionRes>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
