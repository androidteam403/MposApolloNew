package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import android.util.Log;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOmsTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenOrdersPresenter<V extends OpenOrdersMvpView> extends BasePresenter<V>
        implements OpenOrdersMvpPresenter<V> {
    @Inject
    public OpenOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickContinue() {
        getMvpView().onClickContinue();
    }

    @Override
    public void onRackApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RacksDataResponse> call = apiInterface.doRackApiCall();
            call.enqueue(new retrofit2.Callback<RacksDataResponse>() {
                @Override
                public void onResponse(Call<RacksDataResponse> call, Response<RacksDataResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().hideLoading();
//                            getMvpView().onSuccessRackApi(response.body());
                        }
                    }
                    Log.e("TAG", response.code() + "");
                }

                @Override
                public void onFailure(Call<RacksDataResponse> call, Throwable t) {
                    Log.e("Service", "Failed res :: " + t.getMessage());
                    getMvpView().hideLoading();
                    getMvpView().showMessage("Something went wrong");
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickFilterIcon() {
        getMvpView().onClickFilterIcon();
    }

    @Override
    public void fetchFulfilmentOrderList(boolean isRefresh) {
//        if (getMvpView().isNetworkConnected()) {
//            getMvpView().showLoading();
//            getMvpView().hideKeyboard();
//            ApiInterface apiInterface = ApiClient.getApiService2();
////            TransactionHeaderRequest reqModel = new TransactionHeaderRequest();
////            reqModel.setTransactionID("");
////            reqModel.setRefID("");
////            reqModel.setExpiryDays(90);
////            reqModel.setStoreID(getDataManager().getStoreId());
////            reqModel.setTerminalID(getDataManager().getTerminalId());
////            reqModel.setDataAreaID(getDataManager().getDataAreaId());
////            reqModel.setIsMPOS("2");
////            reqModel.setUserName(getDataManager().getUserName());
//            Call<TransactionHeaderResponse> call = apiInterface.GET_OMS_TRANSACTION_HEADER_PICKER_JSON_BLOB();
//            call.enqueue(new Callback<TransactionHeaderResponse>() {
//                @Override
//                public void onResponse(Call<TransactionHeaderResponse> call, Response<TransactionHeaderResponse> response) {
//                    getMvpView().hideLoading();
//                    if (response.isSuccessful()) {
//                        getDataManager().setGlobalTotalOmsTransactionHeader(response.body().getOMSHeader());
//                        getMvpView().setFiltersHeaderLists(response.body().getOMSHeader());
////                        getMvpView().onSucessfullFulfilmentIdList(response.body());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TransactionHeaderResponse> call, Throwable t) {
//                    getMvpView().hideLoading();
//                    handleApiError(t);
//                }
//            });
//        } else {
//            getMvpView().onError("Internet Connection Not Available");
//        }


        //Remove
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            TransactionHeaderRequest reqModel = new TransactionHeaderRequest();
            reqModel.setTransactionID("");
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            reqModel.setIsMPOS("2");
            reqModel.setUserName(""); // getDataManager().getUserName()
            Call<TransactionHeaderResponse> call = apiInterface.GET_OMS_TRANSACTION_HEADER_PICKER(reqModel);
            call.enqueue(new Callback<TransactionHeaderResponse>() {
                @Override
                public void onResponse(Call<TransactionHeaderResponse> call, Response<TransactionHeaderResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        getDataManager().setGlobalTotalOmsTransactionHeader(response.body().getOMSHeader());
                        getMvpView().setFiltersHeaderLists(response.body().getOMSHeader(), isRefresh);
//                        getMvpView().onSucessfullFulfilmentIdList(response.body());
                    }
                }

                @Override
                public void onFailure(Call<TransactionHeaderResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }

    public static boolean isFirstTime = true;
    @Override
    public void onGetOmsTransaction(String fulfilmentId, boolean isItemClick, boolean isAutoAssign, boolean isBulkSelection) {
        if (getMvpView().isNetworkConnected()) {
            if (!isAutoAssign)
//                getMvpView().showLoading();
            if (isBulkSelection) {
                if (isFirstTime) {
                    isFirstTime = false;
                    getMvpView().showLoading();
                }
            } else {
                getMvpView().showLoading();
            }
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            GetOmsTransactionRequest getOmsTransactionRequest = new GetOmsTransactionRequest();
            getOmsTransactionRequest.setDataAreaID(getDataManager().getDataAreaId());
            getOmsTransactionRequest.setExpiryDays(90);
            getOmsTransactionRequest.setRefID("");
            getOmsTransactionRequest.setStoreID(getDataManager().getStoreId());
            getOmsTransactionRequest.setTerminalID(getDataManager().getTerminalId());
            getOmsTransactionRequest.setTransactionID(fulfilmentId);
            Call<List<GetOMSTransactionResponse>> call = apiInterface.getOmsApiCall(getOmsTransactionRequest);
            call.enqueue(new Callback<List<GetOMSTransactionResponse>>() {
                @Override
                public void onResponse(Call<List<GetOMSTransactionResponse>> call, Response<List<GetOMSTransactionResponse>> response) {
                    if (response.isSuccessful()) {
                        if (!isAutoAssign)
//                            getMvpView().hideLoading();
                        if (isAutoAssign) {
                            getMvpView().onSuccessGetOmsTransactionAutoAssign(response.body());
                        } else {
                            if (isItemClick) {
                                getMvpView().hideLoading();
                                getMvpView().onSuccessGetOmsTransactionItemClick(response.body());
                            } else if (isBulkSelection) {
                                getMvpView().onSuccessGetOmsTransactionBulkSelection(response.body());
                            } else {
                                getMvpView().hideLoading();
                                getMvpView().onSucessGetOmsTransaction(response.body());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GetOMSTransactionResponse>> call, Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        }
    }

    @Override
    public void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        getDataManager().setTotalOmsTransactionHeader(totalOmsHeaderList);
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList() {
        return getDataManager().getTotalOmsHeaderList();
    }

    @Override
    public GetGlobalConfingRes getGlobalConfiguration() {
        return getDataManager().getGlobalJson();
    }

    @Override
    public String getUserId() {
        return getDataManager().getUserId();
    }

    @Override
    public void setGlobalTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        getDataManager().setGlobalTotalOmsTransactionHeader(totalOmsHeaderList);
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getGlobalTotalOmsHeaderList() {
        return getDataManager().getGlobalTotalOmsHeaderList();
    }

    @Override
    public void onClickPrevPage() {
        getMvpView().onClickPrevPage();
    }

    @Override
    public void onClickNextPage() {
        getMvpView().onClickNextPage();

    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public List<UserModel._DropdownValueBean> getMaxMinOrdersList() {
        return getDataManager().getMaxMinOrders();
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
                    if (requestType == 2) {
                        order.setRefID("");
                    } else {
                        order.setRefID(selectedOmsHeaderList.get(i).getScannedBarcode());
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

    @Override
    public void onClickSelectAll() {
        getMvpView().onClickSelectAll();
    }
}


