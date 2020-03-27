package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenter<V extends OrdersMvpView> extends BasePresenter<V>
        implements OrdersMvpPresenter<V> {

    @Inject
    public OrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onReturnClick() {
        getMvpView().onReturnClick();
    }

    @Override
    public void onCancelCLick() {
        getMvpView().onCancelCLick();
    }

    @Override
    public void onReOrderClick() {
        getMvpView().onReOrderClick();
    }

    @Override
    public void onItemClick(CalculatePosTransactionRes item) {
        getMvpView().onItemClick(item);
    }

    @Override
    public void onClickSearchIcon() {
        if (!TextUtils.isEmpty(getMvpView().getSearchMobileNumber())) {
            OrderListReq orderListReq = new OrderListReq();
            orderListReq.setArtName("");
            orderListReq.setBatchNo("");
            orderListReq.setCardNo("");
            orderListReq.setCustomerAccount("");
            orderListReq.setCustomerName("");
            orderListReq.setFromDate(null);
            orderListReq.setHomeDelivery(false);
            orderListReq.setIPNumber("");
            orderListReq.setItemID("");
            orderListReq.setMobileNo(getMvpView().getSearchMobileNumber());
            orderListReq.setPendingBills(false);
            orderListReq.setPreviousBills(false);
            orderListReq.setReceiptId("");
            orderListReq.setToDate(null);
            orderServiceCall(orderListReq);
        } else {
            getMvpView().setErrorMessageEditText("Enter Mobile Number");
        }
    }

    @Override
    public void getOrdersDetails() {
        OrderListReq orderListReq = new OrderListReq();
        orderListReq.setArtName("");
        orderListReq.setBatchNo("");
        orderListReq.setCardNo("");
        orderListReq.setCustomerAccount("");
        orderListReq.setCustomerName("");
        orderListReq.setFromDate(CommonUtils.getCurrentDate("dd-MMM-yyyy")); // "13-Mar-2020"
        orderListReq.setHomeDelivery(false);
        orderListReq.setIPNumber("");
        orderListReq.setItemID("");
        orderListReq.setMobileNo("");
        orderListReq.setPendingBills(false);
        orderListReq.setPreviousBills(false);
        orderListReq.setReceiptId("");
        orderListReq.setToDate(CommonUtils.getCurrentDate("dd-MMM-yyyy")); //  "13-Mar-2020"
        orderServiceCall(orderListReq);
    }

    @Override
    public void orderServiceCall(OrderListReq orderListReq) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService();

            Call<ArrayList<CalculatePosTransactionRes>> call = api.ORDER_LIST_RES_CALL(orderListReq);
            call.enqueue(new Callback<ArrayList<CalculatePosTransactionRes>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CalculatePosTransactionRes>> call, @NotNull Response<ArrayList<CalculatePosTransactionRes>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                        getMvpView().hideLoading();
                        getMvpView().onSuccessOrderList(response.body());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().noDataFound();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<CalculatePosTransactionRes>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    getMvpView().showMessage(t.getMessage());
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

}
