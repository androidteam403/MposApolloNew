package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import android.util.Log;
import android.util.Pair;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.FileEntity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.Media_libraryEntity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.Playlist_mediaEntity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchCustomerDoctorDetailsPresenter<V extends SearchCustomerDoctorDetailsMvpView> extends BasePresenter<V>
        implements SearchCustomerDoctorDetailsMvpPresenter<V> {

    @Inject
    public SearchCustomerDoctorDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
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
    public void onActionBarBackPress() {
        getMvpView().onBackPressedClick();
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
            transactionIDModel.setStoreID(getDataManager().getStoreId());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getDataManager().getDataAreaId());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {
                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().showTransactionID(response.body());
                        getCorporateList();
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
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(),getDataManager().getDataAreaId(),new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        getMvpView().getCorporateList(response.body());
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
        if (getDataManager().getlistDataEntity().getRows().size()>0) {
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
