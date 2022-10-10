package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import android.util.Pair;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GeneratePdfbyFlidResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingLabelPresenter<V extends ShippingLabelMvpView> extends BasePresenter<V>
        implements ShippingLabelMvpPresenter<V> {
    @Inject
    public ShippingLabelPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
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
    public void generatePdfbyFlidApiCall(String flid, String paperSize) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getApiService(getDataManager().getEposURL());
            String url = "";
            if (getDataManager().getStoreId().equalsIgnoreCase("16001")) {
                url = "http://lms.apollopharmacy.org:8033/GENERATEPDFFORMPOSUAT/Apollo/SAVEPDF/GENERATEPDFBYFLID/?FLID=" + flid + "&LABELSIZE=" + paperSize;
            } else {
                url = "https://online.apollopharmacy.org/GENERATEPDFFORMPOS/Apollo/SAVEPDF/GENERATEPDFBYFLID?FLID=" + flid + "&LABELSIZE=" + paperSize;
            }
          //  https://online.apollopharmacy.org/GENERATEPDFFORMPOS/Apollo/SAVEPDF/GENERATEPDFBYFLID?FLID=FL20221001301515372&LABELSIZE=A4


            Call<GeneratePdfbyFlidResponse> call = apiInterface.generatePdfByFlidApiCall(url);
            call.enqueue(new Callback<GeneratePdfbyFlidResponse>() {
                @Override
                public void onResponse(Call<GeneratePdfbyFlidResponse> call, Response<GeneratePdfbyFlidResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().onSuccessGeneratepdfbyFlidApiCall(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeneratePdfbyFlidResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public void doDownloadPdf(String pdfUrl, File file) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            Call<ResponseBody> call = api.doDownloadFile(pdfUrl);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        createFilePath(response.body(), file);
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void setPaperLabelSize(String paperLabelSize) {
        getDataManager().setLabelSize(paperLabelSize);
    }

    @Override
    public String getPaperLabelSize() {
        return getDataManager().getLabelSize();
    }

    @Override
    public void onClickScanCode() {
        getMvpView().onClickScanCode();
    }

    @Override
    public void onClickSearchTextClear() {
        getMvpView().onClickSearchTextClear();
    }

    public void createFilePath(ResponseBody body, File destinationFile) {
        try {
            // File destinationFile = new File(FileUtil.createMediaFilePath(fileName, getMvpView().getContext()));
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                //  Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                }
                outputStream.flush();
                //  Log.d(TAG, destinationFile.getParent());

            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                // Log.d(TAG, "Failed to save the file!");
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                getMvpView().showPdf();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //  Log.d(TAG, "Failed to save the file!");
        }
    }
}
