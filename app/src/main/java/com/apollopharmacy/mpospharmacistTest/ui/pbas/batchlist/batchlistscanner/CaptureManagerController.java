package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.app.ProgressDialog;
import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.GetBatchDetailsByBarcodeRequest;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureManagerController {
    private Context mContext;
    private CallbackCaptureManager mCallback;
    private ProgressDialog progressDialog;

    public CaptureManagerController(Context mContext, CallbackCaptureManager mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(mContext);
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(mContext);
    }

    public void getBatchDetailsByBarCode(String barcode, String itemId, String eposurl, String storeId, String dataAreaId, String terminalId, String stateCode) {
        if (isNetworkConnected()) {
            showLoading();
            ApiInterface api = ApiClient.getApiService(eposurl);
            GetBatchDetailsByBarcodeRequest getBatchDetailsByBarcodeRequest = new GetBatchDetailsByBarcodeRequest();
            getBatchDetailsByBarcodeRequest.setArticleCode(itemId);
            getBatchDetailsByBarcodeRequest.setStoreId(storeId);
            getBatchDetailsByBarcodeRequest.setDataAreaId(dataAreaId);
            getBatchDetailsByBarcodeRequest.setTerminalId(terminalId);
            getBatchDetailsByBarcodeRequest.setStoreState(stateCode);
            getBatchDetailsByBarcodeRequest.setCustomerState(stateCode);
            getBatchDetailsByBarcodeRequest.setSez(0);
            getBatchDetailsByBarcodeRequest.setSearchType(1);
            getBatchDetailsByBarcodeRequest.setExpiryDays(30);
            getBatchDetailsByBarcodeRequest.setBarcode(barcode);
            Call<GetBatchInfoRes> call = api.GET_BATCH_DETAILS_BY_BAR_CODE(getBatchDetailsByBarcodeRequest);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        hideLoading();
                        if (response.isSuccessful() && response.body() != null) {
                            for (GetBatchInfoRes.BatchListObj item : response.body().getBatchList()) {
                                item.setBarcodeScannedBatch(true);
                            }
                            mCallback.onSuccessGetBatchDetailsBarcode(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
                    mCallback.onFailure(t.getMessage());
                }
            });
        }
    }
}

