package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    public void getJounalOnlineOrderTransactionApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetJounalOnlineOrderTransactionsRequest getJounalOnlineOrderTransactionsRequest = new GetJounalOnlineOrderTransactionsRequest();
            getJounalOnlineOrderTransactionsRequest.setRequestType(0);
            getJounalOnlineOrderTransactionsRequest.setBulkFilterBy(0);
            getJounalOnlineOrderTransactionsRequest.setFromDate(CommonUtils.getDateTwoDaysEarlier("dd-MMM-yyyy"));
            getJounalOnlineOrderTransactionsRequest.setToDate(CommonUtils.getCurrentDate("dd-MMM-yyyy"));
            getJounalOnlineOrderTransactionsRequest.setCustomerAccount(null);
            getJounalOnlineOrderTransactionsRequest.setReceiptId(null);
            getJounalOnlineOrderTransactionsRequest.setItemID(null);
            getJounalOnlineOrderTransactionsRequest.setHomeDelivery(null);
            getJounalOnlineOrderTransactionsRequest.setMobileNo(null);
            getJounalOnlineOrderTransactionsRequest.setCustomerName(null);
            getJounalOnlineOrderTransactionsRequest.setBatchNo(null);
            getJounalOnlineOrderTransactionsRequest.setArtName(null);
            getJounalOnlineOrderTransactionsRequest.setIPNumber(null);
            getJounalOnlineOrderTransactionsRequest.setCardNo(null);
            getJounalOnlineOrderTransactionsRequest.setISHyperLocal(false);
            getJounalOnlineOrderTransactionsRequest.setPreviousBills(false);
            getJounalOnlineOrderTransactionsRequest.setPendingBills(false);
            getJounalOnlineOrderTransactionsRequest.setRiderRTO(false);
            getJounalOnlineOrderTransactionsRequest.setRinderHandOver(null);
            getJounalOnlineOrderTransactionsRequest.setDeliveryFailRTO(false);
            getJounalOnlineOrderTransactionsRequest.setDspName(null);
            getJounalOnlineOrderTransactionsRequest.setVendorName(null);

            Call<List<GetJounalOnlineOrderTransactionsResponse>> call = api.GET_JOUNAL_ONLINE_ORDER_TRANSACTIONS_API_CALL(getJounalOnlineOrderTransactionsRequest);
            call.enqueue(new Callback<List<GetJounalOnlineOrderTransactionsResponse>>() {
                @Override
                public void onResponse(@NotNull Call<List<GetJounalOnlineOrderTransactionsResponse>> call, @NotNull Response<List<GetJounalOnlineOrderTransactionsResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        getMvpView().onSuccessGetJounalOnlineOrderTransactonApi(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<GetJounalOnlineOrderTransactionsResponse>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void detailorderServiceCall(OrderListReq orderListReq) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<ArrayList<CalculatePosTransactionRes>> call = api.DETAIL_ORDER_LIST(orderListReq);
            call.enqueue(new Callback<ArrayList<CalculatePosTransactionRes>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CalculatePosTransactionRes>> call, @NotNull Response<ArrayList<CalculatePosTransactionRes>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                        getMvpView().hideLoading();
                        getMvpView().onSuccessdetailOrderList(response.body());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().noOrderFound(0);
                        }
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

    @Override
    public void onClickSearchTextClear() {
        getMvpView().onClickSearchTextClear();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }
}
