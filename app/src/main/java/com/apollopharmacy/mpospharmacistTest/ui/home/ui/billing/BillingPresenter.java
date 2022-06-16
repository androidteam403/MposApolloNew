package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing;

import android.text.TextUtils;
import android.util.Pair;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffAPIReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.FileEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Media_libraryEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Playlist_mediaEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingPresenter<V extends BillingMvpView> extends BasePresenter<V>
        implements BillingMvpPresenter<V> {

    @Inject
    public BillingPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCustomerSearchClick() {
        getMvpView().onCustomerSearchClick();
    }

    @Override
    public void onDoctorSearchClick() {
        getMvpView().onDoctorSearchClick();
    }

    @Override
    public void onCorporateSearchClick() {
        getMvpView().onCorporateSearchClick();
    }

    @Override
    public void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity) {
        getMvpView().customerEditClick(customerEntity);
    }

    @Override
    public void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        getMvpView().onDoctorEditClick(doctorEntity, salesEntity);
    }

    @Override
    public void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity) {
        getMvpView().onCorporateEditClick(corporateEntity);
    }

    @Override
    public void onContinueBtnClick() {
        getMvpView().onContinueBtnClick();
    }

    @Override
    public void getTransactionID() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            TransactionIDReqModel transactionIDModel = new TransactionIDReqModel();
            transactionIDModel.setRequestStatus(0);
            transactionIDModel.setReturnMessage("");
            transactionIDModel.setResultValue("");
            transactionIDModel.setTransactionID("");
            transactionIDModel.setStoreID(getDataManager().getGlobalJson().getStoreID());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getDataManager().getGlobalJson().getDataAreaID());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {

                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().showTransactionID(response.body());
                        getCorporateList();
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        } else {
                            getMvpView().showMessage(R.string.some_error);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TransactionIDResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            //getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().getCorporateList(response.body());
                        getDoctorsList();
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
    public void getDoctorsList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            DoctorSearchReqModel doctorSearchModel = new DoctorSearchReqModel();
            doctorSearchModel.setISAX(false);
            doctorSearchModel.setDoctorID("");
            doctorSearchModel.setDoctorName("");
            doctorSearchModel.setClusterId(getDataManager().getGlobalJson().getClusterCode());
            doctorSearchModel.setDoctorBaseUrl(getDataManager().getGlobalJson().getDoctorSearchUrl());
            Call<DoctorSearchResModel> call = api.getDoctorsList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), doctorSearchModel);
            call.enqueue(new Callback<DoctorSearchResModel>() {
                @Override
                public void onResponse(@NotNull Call<DoctorSearchResModel> call, @NotNull Response<DoctorSearchResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getSalesOrigin();
                        getMvpView().getDoctorSearchList(response.body());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DoctorSearchResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getSalesOrigin() {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<SalesOriginResModel> call = api.getSalesOriginList(getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<SalesOriginResModel>() {
                @Override
                public void onResponse(@NotNull Call<SalesOriginResModel> call, @NotNull Response<SalesOriginResModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getGetSalesOriginResult().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().getSalesOriginList(response.body());
                        for (int i = 0; i < response.body().getGetSalesOriginResult().get_DropdownValue().size(); i++) {
                            getMvpView().getSalesListDropDown(response.body());
                        }
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getGetSalesOriginResult().getRequestStatus());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SalesOriginResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public String getStoreName() {
        return getDataManager().getGlobalJson().getStoreName();
    }

    @Override
    public String getStoreId() {
        return getDataManager().getStoreId();
    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public void getPharmacyStaffApiDetails(String action) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            PharmacyStaffAPIReq staffAPIReq = new PharmacyStaffAPIReq();
            staffAPIReq.setAction(action);
            staffAPIReq.setAmount(0.0);
            staffAPIReq.setDocNum("");

            if (TextUtils.isEmpty(getMvpView().getPrgTracing())) {
//                getMvpView().corpPrgTrackingError();
                getMvpView().hideLoading();
                return;
            } else
                staffAPIReq.setEmpId(getMvpView().getPrgTracing());

            staffAPIReq.setMobileNum("");
            staffAPIReq.setOTP("");
            staffAPIReq.setRegion(getDataManager().getGlobalJson().getRegion());
            staffAPIReq.setSiteId(getDataManager().getGlobalJson().getStoreID());
            staffAPIReq.setSiteName(getDataManager().getGlobalJson().getStoreName());
            staffAPIReq.setUrl(getDataManager().getGlobalJson().getDSBillingURL());
            Call<PharmacyStaffApiRes> call = api.PHARMACY_STAFF_API_RES_CALL(staffAPIReq);
            call.enqueue(new Callback<PharmacyStaffApiRes>() {
                @Override
                public void onResponse(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Response<PharmacyStaffApiRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0 && response.body().getMessage().equalsIgnoreCase("Data Founds")) {
                            if (action.equalsIgnoreCase("ENQUIRY")) {
//                                double availableAmount = Double.parseDouble(response.body().getTotalBalance()) - Double.parseDouble(response.body().getUsedBalance());
                                getMvpView().onSucessStaffListData(response.body());
                            }
                        } else {
                            getMvpView().onFaliureStaffListData();
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<PharmacyStaffApiRes> call, @NotNull Throwable t) {
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
    public void onUploadApiCall() {
        getMvpView().onUploadApiCall();
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
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getData().getListData().getRows().size() > 0) {
                            getDataManager().setListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessPlayList();
                            getMvpView().hideLoading();

                        } else {

                            ListDataEntity listDataEntity = new ListDataEntity();
                            ArrayList<RowsEntity> rowsEntitiesList = new ArrayList<>();
                            RowsEntity rowsEntity = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList = new ArrayList<>();
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setName("mpos_slide_1.jpg");
                            fileEntity.setPath("68B90B3AE0B17AB27D4BB366E50805A2084174193671261F134F22B087221BE64160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList.add(fileEntity);
                            media_libraryEntity.setFile(fileEntitiesList);
                            playlist_mediaEntity.setMedia_library(media_libraryEntity);
                            rowsEntity.setPlaylist_media(playlist_mediaEntity);
                            rowsEntitiesList.add(rowsEntity);

                            RowsEntity rowsEntity1 = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity1 = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity1 = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList1 = new ArrayList<>();
                            FileEntity fileEntity1 = new FileEntity();
                            fileEntity1.setName("mpos_slide_2.jpg");
                            fileEntity1.setPath("6864B2FA96DB3C2820A9BFA34F0EDF3279AFBA20F9E5EE6F6B54B2BD7D539F604160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList1.add(fileEntity1);
                            media_libraryEntity1.setFile(fileEntitiesList1);
                            playlist_mediaEntity1.setMedia_library(media_libraryEntity1);
                            rowsEntity1.setPlaylist_media(playlist_mediaEntity1);
                            rowsEntitiesList.add(rowsEntity1);

                            RowsEntity rowsEntity2 = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity2 = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity2 = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList2 = new ArrayList<>();
                            FileEntity fileEntity2 = new FileEntity();
                            fileEntity2.setName("mpos_slide_3.jpg");
                            fileEntity2.setPath("E10F35599A534C1A870654298BDF66B3171ADE8503248AFDE81D19DC013B029C4160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList2.add(fileEntity2);
                            media_libraryEntity2.setFile(fileEntitiesList2);
                            playlist_mediaEntity2.setMedia_library(media_libraryEntity2);
                            rowsEntity2.setPlaylist_media(playlist_mediaEntity2);
                            rowsEntitiesList.add(rowsEntity2);

                            listDataEntity.setRows(rowsEntitiesList);
                            response.body().getData().setListData(listDataEntity);
                            getDataManager().setListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessPlayList();
                            getMvpView().hideLoading();
                        }
                    } else {
                        getMvpView().hideLoading();
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
        if (getDataManager().getlistDataEntity().getRows().size() > 0) {
            return getDataManager().getlistDataEntity().getRows();
        } else {
            return null;
        }
    }
    @Override
    public boolean enablescreens()
    {
        return getDataManager().isOpenScreens();
    }

    @Override
    public void getUHIDDetails(String uhId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetHBPUHIDDetailsRequest staffAPIReq = new GetHBPUHIDDetailsRequest();
            staffAPIReq.setClusterCode(getDataManager().getGlobalJson().getClusterCode());
            staffAPIReq.setSiteId(getDataManager().getGlobalJson().getStoreID());
            staffAPIReq.setUHID(uhId);
            staffAPIReq.setURL(getDataManager().getGlobalJson().getUHIDHBPURL());


            Call<GetHBPUHIDDetailsResponse> call = api.HBPUHID_DETAILS_RESPONSE_CALL(staffAPIReq);
            call.enqueue(new Callback<GetHBPUHIDDetailsResponse>() {
                @Override
                public void onResponse(@NotNull Call<GetHBPUHIDDetailsResponse> call, @NotNull Response<GetHBPUHIDDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().isStatus() ) {
                            getMvpView().updateUHIDDetails(response.body().getUhid_details());
                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetHBPUHIDDetailsResponse> call, @NotNull Throwable t) {
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
    public HBPConfigResponse getHBPConfing() {
        return getDataManager().getHBPConfigRes();
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
             //   Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                }
                outputStream.flush();
               // Log.d(TAG, destinationFile.getParent());
//                getMvpView().checkFileAvailability();
                getMvpView().onSucessPlayList();
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
               // Log.d(TAG, "Failed to save the file!");
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
           // Log.d(TAG, "Failed to save the file!");
        }
    }

}