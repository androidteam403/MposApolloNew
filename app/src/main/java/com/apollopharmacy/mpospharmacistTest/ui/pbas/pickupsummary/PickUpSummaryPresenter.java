package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.ForwardToPickerRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpSummaryPresenter<V extends PickUpSummaryMvpView> extends BasePresenter<V>
        implements PickUpSummaryMvpPresenter<V> {

    @Inject
    public PickUpSummaryPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void setFullfillmentData(List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList) {
        getDataManager().setFullFillmentList(fullfillmentDetailList);
    }

    @Override
    public List<RacksDataResponse.FullfillmentDetail> getFullFillmentList() {
        return getDataManager().getFullFillmentList();
    }

    @Override
    public void ForwardToPickerRequest(ForwardToPickerRequest request) {

    }

    @Override
    public void UpdateOmsOrder(OMSOrderForwardRequest omsOrderForwardRequest) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            omsOrderForwardRequest.setTerminalID(getDataManager().getTerminalId());

            //ApiInterface api = ApiClient.getApiService(Constant.UPDATEOMSORDER);
            // text.replace("/"","");
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");

            }
            // ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ApiInterface api = ApiClient.getApiService(replace_url);

            // ApiInterface api = ApiClient.getApiService3();
            Call<OMSOrderForwardResponse> call = api.UPDATE_OMS_ORDER(omsOrderForwardRequest);
            call.enqueue(new Callback<OMSOrderForwardResponse>() {
                @Override
                public void onResponse(@NotNull Call<OMSOrderForwardResponse> call, @NotNull Response<OMSOrderForwardResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().OmsOrderUpdateSuccess(response.body());

                        } else {
                            getMvpView().OmsOrderUpdateFailure(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<OMSOrderForwardResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }


    @Override
    public void setListOfListFullfillmentData(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullfillmentDetailList) {
        getDataManager().setfullFillListOfListFiltered(listOfListFullfillmentDetailList);
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList() {
        return getDataManager().getfullFillListOfListFiltered();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }

    @Override
    public void onClickUpdateOMSOrder_pickingconfirmation() {
        getMvpView().onClickUpdateOMSOrder_pickingconfirmation();
    }

    @Override
    public void mposPickPackOrderReservationApiCall(int requestType, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(getDataManager().getUserName());
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
                    order.setDataAreaID("AHEL");
                    order.setStoreID(getDataManager().getStoreId());
                    order.setTerminalID(getDataManager().getTerminalId());
                    order.setTransactionID(selectedOmsHeaderList.get(i).getRefno());
                    order.setRefID(selectedOmsHeaderList.get(i).getScannedBarcode());
                    ordersList.add(order);
                }
            }

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");

            }
            ApiInterface api = ApiClient.getApiService(replace_url);

            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse(@NotNull Call<MPOSPickPackOrderReservationResponse> call, @NotNull Response<MPOSPickPackOrderReservationResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());

                        } else {
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
}
