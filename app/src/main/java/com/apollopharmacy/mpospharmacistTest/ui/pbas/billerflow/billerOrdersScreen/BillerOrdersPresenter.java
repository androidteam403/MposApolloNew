package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.util.Log;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillerOrdersPresenter<V extends BillerOrdersMvpView> extends BasePresenter<V>
        implements BillerOrdersMvpPresenter<V> {

    @Inject
    public BillerOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
        int i = 0;
    }


    @Override
    public void onClickFilterIcon() {
        getMvpView().onClickFilterIcon();
    }

    @Override
    public void fetchFulfilmentOrderList() {
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
            reqModel.setIsMPOS("3");//3
            reqModel.setUserName(getDataManager().getUserName());

            Call<OMSTransactionHeaderResModel> call = api.GET_OMS_TRANSACTION_HEADER(reqModel);
            call.enqueue(new Callback<OMSTransactionHeaderResModel>() {
                @Override
                public void onResponse(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Response<OMSTransactionHeaderResModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getOMSHeaderArr() != null && response.body().getOMSHeaderArr().size()>1) {
                            Collections.sort(response.body().getOMSHeaderArr(), new Comparator<OMSTransactionHeaderResModel.OMSHeaderObj>() {
                                public int compare(OMSTransactionHeaderResModel.OMSHeaderObj o1, OMSTransactionHeaderResModel.OMSHeaderObj o2) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                                    Date date1 = null;
                                    Date date2 = null;
                                    try {
                                        date1 = dateFormat.parse(o1.getDeliveryDate());
                                        date2 = dateFormat.parse(o2.getDeliveryDate());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    return date1.compareTo(date2);
                                }
                            });
                        }
                        getMvpView().onSucessfullFulfilmentIdList(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OMSTransactionHeaderResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }


//        if (getMvpView().isNetworkConnected()) {
//            getMvpView().showLoading();
//            getMvpView().hideKeyboard();
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            TransactionHeaderRequest reqModel = new TransactionHeaderRequest();
//            reqModel.setTransactionID("");
//            reqModel.setRefID("");
//            reqModel.setExpiryDays(90);
//            reqModel.setStoreID(getDataManager().getStoreId());
//            reqModel.setTerminalID(getDataManager().getTerminalId());
//            reqModel.setDataAreaID(getDataManager().getDataAreaId());
//            reqModel.setIsMPOS(getDataManager().getGlobalJson().getMPOSVersion());
//            reqModel.setUserName(getDataManager().getUserName());
//            Call<TransactionHeaderResponse> call = apiInterface.GET_OMS_TRANSACTION_HEADER_PICKER(reqModel);
//            call.enqueue(new Callback<TransactionHeaderResponse>() {
//                @Override
//                public void onResponse(Call<TransactionHeaderResponse> call, Response<TransactionHeaderResponse> response) {
//                    getMvpView().hideLoading();
//                    if (response.isSuccessful()) {
//                        if (response.body() != null)
//                            getMvpView().onSucessfullFulfilmentIdList(response.body());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TransactionHeaderResponse> call, Throwable t) {
//                    getMvpView().hideLoading();
//                    handleApiError(t);
//                }
//            });
//        }

    }


    @Override
    public void onclickScanCode() {
        getMvpView().onclickScanCode();
    }

    @Override
    public List<OMSTransactionHeaderResModel.OMSHeaderObj> getTotalOmsHeaderList() {
        return getDataManager().getTotalOmsHeaderListObj();
    }

    @Override
    public void setTotalOmsHeaderList(List<OMSTransactionHeaderResModel.OMSHeaderObj> totalOmsHeaderList) {
        getDataManager().setTotalOmsTransactionHeaderObj(totalOmsHeaderList);
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
                            getMvpView().onSuccessRackApi(response.body());
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
    public void mposPickPackOrderReservationApiCall(OMSTransactionHeaderResModel.OMSHeaderObj omsHeaderObj) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(10);
            mposPickPackOrderReservationRequest.setUserName(getDataManager().getUserName());
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
            order.setDataAreaID(getDataManager().getDataAreaId());
            order.setStoreID(getDataManager().getStoreId());
            order.setTerminalID(getDataManager().getTerminalId());
            order.setTransactionID(omsHeaderObj.getREFNO());
            order.setRefID("");
            ordersList.add(order);


            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("MPOS/")) {
                replace_url = check_epos.replace("MPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");
                replace_url = check_epos.replace("9880", "9887");

            }

            ApiInterface api = ApiClient.getApiService(replace_url);
            String url = "";
            //getDataManager().getStoreId().equalsIgnoreCase("16001") &&
            if (getDataManager().getEposURL().equalsIgnoreCase("http://online.apollopharmacy.org:51/MPOS/")) {
                url = "OMSSERVICE/OMSService.svc/MPOSPickPackOrderReservation";
            } else {
                url = "OMSService.svc/MPOSPickPackOrderReservation";
            }

            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(url, mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse
                        (@NotNull Call<MPOSPickPackOrderReservationResponse> call, @NotNull Response<MPOSPickPackOrderReservationResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().onSuccessMposPickPackOrderReservationApiCall(response.body());
                        } else {
                            getMvpView().onError("Something went wrong.");
                        }

                    }
                }

                @Override
                public void onFailure(Call<MPOSPickPackOrderReservationResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }
}

