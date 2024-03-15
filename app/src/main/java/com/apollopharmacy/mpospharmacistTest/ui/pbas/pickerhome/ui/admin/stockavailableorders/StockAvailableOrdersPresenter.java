package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockAvailableOrdersPresenter<V extends StockAvailableOrdersMvpView> extends BasePresenter<V> implements StockAvailableOrdersMvpPresenter<V> {
    @Inject
    public StockAvailableOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBack() {
        getMvpView().onClickBack();
    }

    @Override
    public void mposPickPackOrderReservationApiCall(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList, int requestType, String user) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(user);
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                for (int i = 0; i < omsHeaderList.size(); i++) {
                    MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
                    order.setDataAreaID(getDataManager().getDataAreaId());
                    order.setStoreID(getDataManager().getStoreId());
                    order.setTerminalID(getDataManager().getTerminalId());
                    order.setTransactionID(omsHeaderList.get(i).getRefno());
                    if (requestType == 2) {
                        order.setRefID("");
                    }
                    ordersList.add(order);
                }
            }
            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("MPOS/")) {
                replace_url = check_epos.replace("MPOS/", "");
            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");
            }
            ApiInterface api = ApiClient.getApiService(replace_url);
            String url = "";
            if (getDataManager().getEposURL().equalsIgnoreCase("http://online.apollopharmacy.org:51/MPOS/")) {
                url = "OMSSERVICE/OMSService.svc/MPOSPickPackOrderReservation";
            } else {
                url = "OMSService.svc/MPOSPickPackOrderReservation";
            }
            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(url, mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse(Call<MPOSPickPackOrderReservationResponse> call, Response<MPOSPickPackOrderReservationResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MPOSPickPackOrderReservationResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public List<UserModel._DropdownValueBean> getLoginUserDetails() {
        return getDataManager().getMaxMinOrders();
    }

    @Override
    public void getOmsTransactionHeader() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetOMSTransactionHeaderRequest getOMSTransactionHeaderRequest = new GetOMSTransactionHeaderRequest();
            getOMSTransactionHeaderRequest.setIsMPOS("2");
            getOMSTransactionHeaderRequest.setUserName("");
            getOMSTransactionHeaderRequest.setTransactionID("");
            getOMSTransactionHeaderRequest.setRefID("");
            getOMSTransactionHeaderRequest.setExpiryDays(90);
            getOMSTransactionHeaderRequest.setStoreID(getDataManager().getStoreId());
            getOMSTransactionHeaderRequest.setTerminalID(getDataManager().getTerminalId());
            getOMSTransactionHeaderRequest.setDataAreaID(getDataManager().getDataAreaId());
            Call<GetOMSTransactionHeaderResponse> call = apiInterface.getOmsTransactionHeaderApiCall(getOMSTransactionHeaderRequest);
            call.enqueue(new Callback<GetOMSTransactionHeaderResponse>() {
                @Override
                public void onResponse(Call<GetOMSTransactionHeaderResponse> call, Response<GetOMSTransactionHeaderResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().hideLoading();
                            getMvpView().onSuccessGetOmsTransactionHeader(response.body().getOMSHeader());
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetOMSTransactionHeaderResponse> call, Throwable t) {

                }
            });
        }
    }
}
