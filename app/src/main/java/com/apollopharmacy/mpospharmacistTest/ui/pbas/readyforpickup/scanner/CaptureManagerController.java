package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner;

import android.app.ProgressDialog;
import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureManagerController {

    private Context mContext;
    private CallbackCaptureManager mCallback;
    private ProgressDialog mProgressDialog;

    public CaptureManagerController(Context mContext, CallbackCaptureManager mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    private boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(mContext);
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(mContext);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }


    public void mposPickPackOrderReservationApiCall(int requestType, TransactionHeaderResponse.OMSHeader selectedOmsHeader, String userName, String storeId, String terminalId, String eposUrl, String barcode) {
        if (isNetworkConnected()) {
            showLoading();
            MPOSPickPackOrderReservationRequest mposPickPackOrderReservationRequest = new MPOSPickPackOrderReservationRequest();
            mposPickPackOrderReservationRequest.setRequestType(requestType);
            mposPickPackOrderReservationRequest.setUserName(userName);
            List<MPOSPickPackOrderReservationRequest.Order> ordersList = new ArrayList<>();
            MPOSPickPackOrderReservationRequest.Order order = new MPOSPickPackOrderReservationRequest.Order();
            order.setDataAreaID("AHEL");
            order.setStoreID(storeId);
            order.setTerminalID(terminalId);
            order.setTransactionID(selectedOmsHeader.getRefno());
            order.setRefID(barcode);
            ordersList.add(order);

            mposPickPackOrderReservationRequest.setOrderList(ordersList);
            String check_epos = eposUrl;
            String replace_url = eposUrl;
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");
                replace_url = check_epos.replace("9880", "9887");

            }
            ApiInterface api = ApiClient.getApiService(replace_url);

            Call<MPOSPickPackOrderReservationResponse> call = api.OMS_PICKER_PACKER_ORDER_RESERVATION(mposPickPackOrderReservationRequest);
            call.enqueue(new Callback<MPOSPickPackOrderReservationResponse>() {
                @Override
                public void onResponse(@NotNull Call<MPOSPickPackOrderReservationResponse> call, @NotNull Response<MPOSPickPackOrderReservationResponse> response) {
                    hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            mCallback.onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());
                        } else {
                            mCallback.onSuccessMposPickPackOrderReservationApiCall(requestType, response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<MPOSPickPackOrderReservationResponse> call, Throwable t) {
                    hideLoading();
                    mCallback.onFailureMessage(t.getMessage());
                }
            });
        }
    }
}
