package com.apollopharmacy.mpospharmacist.ui.home;

import android.util.Log;
import android.util.Pair;

import com.apollopharmacy.mpospharmacist.BuildConfig;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivityPresenter<V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityMvpPresenter<V> {
    @Inject
    public MainActivityPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName() + "\n" + getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {
        return getDataManager().getGlobalJson().getStoreName() + "\n" + getDataManager().getStoreId();
    }

    @Override
    public void logoutUser() {
        getDataManager().logoutUser();
        getMvpView().navigateLoginActivity();
    }

    @Override
    public void onCheckBuildDetails() {
        VendorCheckRes.BUILDDETAILSEntity buildDetailsEntity = getDataManager().getVendorRes().getBUILDDETAILS();
        if (buildDetailsEntity != null) {
            if (buildDetailsEntity.getAPPAVALIBALITY()) {
                if (Double.parseDouble(buildDetailsEntity.getBUILDVERSION()) > Double.parseDouble((BuildConfig.VERSION_NAME))) {
                    if (buildDetailsEntity.getFORCEDOWNLOAD()) {
                        getMvpView().displayAppInfoDialog("Update Available", buildDetailsEntity.getBUILDMESSAGE(), "Update", "");
                    } else {
                        getMvpView().displayAppInfoDialog("Update Available", buildDetailsEntity.getBUILDMESSAGE(), "Update Now", "Later");
                    }
                }
            } else {
                getMvpView().displayAppInfoDialog("Info", buildDetailsEntity.getAVABILITYMESSAGE(), "", "");
            }
        }
    }

    @Override
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().getCorporateList(response.body());
                        getUnpostedTransaction();
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getUnpostedTransaction() {
        if (getMvpView().isNetworkConnected()) {
            //  getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.GET_UNPOSTED_TRANSACTION(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful()) {
                        //   getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            if (response.body().getSalesLine() != null && response.body().getSalesLine().size() > 0)
                                getMvpView().onSuccessGetUnPostedPOSTransaction(response.body());
                        } else
                            getMvpView().hideLoading();
//                        else
//                            getMvpView().showMessage(response.body() != null ? response.body().getReturnMessage() : "");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public boolean isKisokMode() {
        return getDataManager().isKioskMode();
    }

    @Override
    public void setKioskMode(boolean isKioskMode) {
        getDataManager().setKioskMode(isKioskMode);
    }

    @Override
    public void onMposTabApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            ADSPlayListRequest adsPlayListRequest = new ADSPlayListRequest();
            adsPlayListRequest.setScreen_id("MPOS_TAB");
            Call<ADSPlayListResponse> call = api.ADS_PLAY_LIST_RESPONSE_SINGLE(adsPlayListRequest);
            call.enqueue(new Callback<ADSPlayListResponse>() {
                @Override
                public void onResponse(@NotNull Call<ADSPlayListResponse> call, @NotNull Response<ADSPlayListResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getData().getListData().getRows() != null) {
                        getDataManager().setListDataEntity(response.body().getData().getListData());
                        getMvpView().onSucessPlayList();
                        getMvpView().hideLoading();

                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ADSPlayListResponse> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


    public List<RowsEntity> getDataListEntity() {
        if (getDataManager().getlistDataEntity().getRows() != null) {
            return getDataManager().getlistDataEntity().getRows();
        } else {
            return null;
        }
    }


    @Override
    public void onDownloadApiCall(String filePath, String fileName, int pos) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            Call<ResponseBody> call = api.doDownloadFile("https://signage.apollopharmacy.app/zc-v3.1-fs-svc/2.0/ads/get/" + filePath);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        createFilePath(response.body(), fileName, true, pos);
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
    public void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos) {
        try {
            File destinationFile = new File(FileUtil.createMediaFilePath(fileName, getMvpView().getContext()));
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                }
                outputStream.flush();
                Log.d(TAG, destinationFile.getParent());
//                getMvpView().checkFileAvailability();
                getMvpView().onSucessPlayList();
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                Log.d(TAG, "Failed to save the file!");
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
        }
    }
}
