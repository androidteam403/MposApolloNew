package com.apollopharmacy.mpospharmacistTest.ui.zebrascanner;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
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

public class ZebrascannerPresenter<V extends  ZebrascannerMvpView> extends BasePresenter<V>
        implements ZebrascannerMvpPresenter<V>
{
    @Inject
    public ZebrascannerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void scanqrcode(String s) {
        getMvpView().scanqrcode(s);
    }

    @Override
    public void dialogShow(int orderPos) {
        getMvpView().dialogShow(orderPos);
    }

    @Override
    public  void scannedListener(List<String> barcodeList)
    {
        getMvpView().scannedListener(barcodeList);
    }

    @Override
    public  void onClickScanCode(String s, String refno)
    {
        getMvpView().onClickScanCode(s,refno);
    }
    public void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeader, String userName, String storeId, String terminalId, String eposUrl, String barcode, String dataAreaId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(userName);
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
            order.setDataAreaID(dataAreaId);
            order.setStoreID(storeId);
            order.setTerminalID(terminalId);
            order.setTransactionID(selectedOmsHeader.getRefno());
            order.setRefID(barcode);
            ordersList.add(order);

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = eposUrl;
            String replace_url = eposUrl;
            if (check_epos.contains("MPOS/")) {
                replace_url = check_epos.replace("MPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");
                replace_url = check_epos.replace("9880", "9887");

            }
            ApiInterface api = ApiClient.getApiService(replace_url);
            String url = "";
            //storeId.equalsIgnoreCase("16001") &&
            if (eposUrl.equalsIgnoreCase("http://online.apollopharmacy.org:51/MPOS/")) {
                url = "OMSSERVICE/OMSService.svc/MPOSPickPackOrderReservation";
            } else {
                url = "OMSService.svc/MPOSPickPackOrderReservation";
            }

            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(url, mposPickPackOrderReservationRequest);
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
                  //  onFailureMessage(t.getMessage());
                }
            });
        }
    }
}
