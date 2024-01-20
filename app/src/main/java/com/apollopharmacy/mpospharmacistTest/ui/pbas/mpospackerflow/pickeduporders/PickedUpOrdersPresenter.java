package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
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

public class PickedUpOrdersPresenter<V extends PickedUpOrdersMvpView> extends BasePresenter<V>
        implements PickedUpOrdersMvpPresenter<V> {

    @Inject
    public PickedUpOrdersPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void startPickUp() {
        getMvpView().startPickUp();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }

    @Override
    public void fetchOMSOrderList() {

    }

    @Override
    public GetGlobalConfingRes getGlobalConfigRes() {
        return null;
    }

    @Override
    public void fetchFulfilmentOrderList() {
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
            reqModel.setIsMPOS("4");//1
            reqModel.setUserName(getDataManager().getUserName());
            Call<TransactionHeaderResponse> call = apiInterface.GET_OMS_TRANSACTION_HEADER_PICKER(reqModel);
            call.enqueue(new Callback<TransactionHeaderResponse>() {
                @Override
                public void onResponse(Call<TransactionHeaderResponse> call, Response<TransactionHeaderResponse> response) {
                    if (response.isSuccessful()) {
//                        for (int i = 0; i < response.body().getOMSHeader().size(); i++) {
//                            response.body().getOMSHeader().get(i).setOrderPickup(true);
//                            response.body().getOMSHeader().get(i).setOrderPacked(false);
//                        }
                        if (response.body() != null && response.body().getOMSHeader() != null && response.body().getOMSHeader().size()>1) {
                            Collections.sort(response.body().getOMSHeader(), new Comparator<TransactionHeaderResponse.OMSHeader>() {
                                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
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
                        getMvpView().hideLoading();
                        getDataManager().setGlobalTotalOmsTransactionHeader(response.body().getOMSHeader());
                        getMvpView().setFiltersHeaderLists(response.body().getOMSHeader());
//                        getMvpView().onSucessfullFulfilmentIdList(response.body());
                    } else {
                        getMvpView().hideLoading();
                    }
                }

                @Override
                public void onFailure(Call<TransactionHeaderResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public void mposPickPackOrderReservationApiCall(TransactionHeaderResponse.OMSHeader omsHeaderObj) {
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
            order.setTransactionID(omsHeaderObj.getRefno());
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
    public void onClickPrevPage() {
        getMvpView().onClickPrevPage();
    }

    @Override
    public void onClickNextPage() {
        getMvpView().onClickNextPage();
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
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public void onClickScanBarCode() {
        getMvpView().onClickScanBarCode();
    }


    @Override
    public void setTotalOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        getDataManager().setTotalOmsTransactionHeader(totalOmsHeaderList);
    }

    @Override
    public List<RacksDataResponse.FullfillmentDetail> getFullFillmentList() {
        return getDataManager().getFullFillmentList();
    }

    @Override
    public void onClickFilter() {
        getMvpView().onClickFilterIcon();
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> getListOfListFullFillmentList() {
        return getDataManager().getfullFillListOfListFiltered();
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList() {
        return getDataManager().getTotalOmsHeaderList();

    }

    @Override
    public void setFullFillmentDataList(List<RacksDataResponse.FullfillmentDetail> fullFillmentDataList) {
        getDataManager().setFullFillmentList(fullFillmentDataList);
    }

    @Override
    public void setListOfListFullFillProducts(List<List<RackAdapter.RackBoxModel.ProductData>> listOfListFullFillProducts) {
        getDataManager().setfullFillListOfListFiltered(listOfListFullFillProducts);
    }


}
