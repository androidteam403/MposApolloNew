package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin;

import android.util.Pair;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacistTest.BuildConfig;
import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.FileEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Media_libraryEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Playlist_mediaEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UpdatePatchRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UpdatePatchResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
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

public class PharmacistLoginPresenter<V extends PharmacistLoginMvpView> extends BasePresenter<V> implements PharmacistLoginMvpPresenter<V> {
    @Inject
    public PharmacistLoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickLogin() {
        getMvpView().onClickLogin();
    }

    @Override
    public void onClickCampaignClose() {
        getMvpView().onClickCampaignClose();
    }

    @Override
    public void onInstoreCLick() {
        getMvpView().onClickInstore();
    }

    @Override
    public void onSelectCampaign() {
        getCampaign();
    }

    @Override
    public void getUserId() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            /*getDataManager().setStoreId("16001");
            getDataManager().setTerminalId("002");
            getDataManager().setDataAreaId("AHEL");
            getDataManager().setAdminSetUpFinish(true);*/

            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<UserModel> call = api.getUserIds(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());

            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().getGetLoginUserResult().getRequestStatus() == 0) {
                            getMvpView().getUserIds(response.body());
                            getDataManager().storeEposUrl(true);
//                            response.body().getGetLoginUserResult().get_DropdownValue().get(0).setMaximumOrders("10");
//                            response.body().getGetLoginUserResult().get_DropdownValue().get(0).setMinimumOrders("3");
                            getDataManager().setMaxMinOrders(response.body().getGetLoginUserResult().get_DropdownValue());
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getGetLoginUserResult().getReturnMessage());
                            } else {
                                getMvpView().showMessage(R.string.some_error);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
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
    public void getCampaign() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService2();
            Call<CampaignDetailsRes> call = api.CAMPAIGN_DETAILS_RES_CALL(getDataManager().getStoreId());
            call.enqueue(new Callback<CampaignDetailsRes>() {
                @Override
                public void onResponse(@NotNull Call<CampaignDetailsRes> call, @NotNull Response<CampaignDetailsRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        getMvpView().setCampaignDetails(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CampaignDetailsRes> call, @NotNull Throwable t) {
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
    public void userLoginInStoreApi() {
        if (getMvpView().isNetworkConnected()) {

            //Creating an object of our api interface
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            LoginReqModel loginReqModel = new LoginReqModel();
            loginReqModel.setUserID(getMvpView().getUserId());
            loginReqModel.setStoreID(getDataManager().getStoreId());
            loginReqModel.setTerminalID(getDataManager().getTerminalId());
            loginReqModel.setPassword(getMvpView().getUserPassword());
            Call<LoginResModel> call = api.LOGIN_RES_MODEL_CALL(loginReqModel);
            call.enqueue(new Callback<LoginResModel>() {
                @Override
                public void onResponse(@NotNull Call<LoginResModel> call, @NotNull Response<LoginResModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        /*getDataManager().setUserId(getMvpView().getUserId());
                        getDataManager().setUserName(getMvpView().getUserId());
                        getDataManager().setUserLogin(true);
                        getGlobalConfigration();*/
                        if (response.body().getRequestStatus() == 0) {
                            getDataManager().setUserId(response.body().getUserId());
                            getDataManager().setUserName(response.body().getUserName());
                            getDataManager().setUserLogin(true);
                            getGlobalConfigration();
                        } else {
                            getMvpView().userLoginFailed(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResModel> call, @NotNull Throwable t) {
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
    public void userLoginCampaignApi() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            LoginReqModel loginReqModel = new LoginReqModel();
            loginReqModel.setUserID(getMvpView().getUserId());
            loginReqModel.setStoreID(getDataManager().getStoreId());
            loginReqModel.setTerminalID(getDataManager().getTerminalId());
            loginReqModel.setPassword(getMvpView().getUserPassword());
            Call<LoginResModel> call = api.LOGIN_RES_MODEL_CALL(loginReqModel);
            call.enqueue(new Callback<LoginResModel>() {
                @Override
                public void onResponse(@NotNull Call<LoginResModel> call, @NotNull Response<LoginResModel> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.body().getRequestStatus() == 0) {
                            getDataManager().setUserId(response.body().getUserId());
                            getDataManager().setUserName(response.body().getUserName());
                            getDataManager().setUserLogin(true);
                            getGlobalConfigration();
                        } else {
                            getMvpView().userLoginFailed(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResModel> call, @NotNull Throwable t) {
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
    public void getGlobalConfigration() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<GetGlobalConfingRes> call = api.GET_GLOBAL_CONFING_RES_CALL(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<GetGlobalConfingRes>() {
                @Override
                public void onResponse(@NotNull Call<GetGlobalConfingRes> call, @NotNull Response<GetGlobalConfingRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        // getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
//                            response.body().setMPOSVersion("1");
//                            response.body().getOMSVendorWiseConfigration().get(3).setAllowMultiBatch(false);
//                            response.body().getOMSVendorWiseConfigration().get(3).setAllowChangeQTY(false);
//                            response.body().setISHBPStore(true);
                            response.body().setISHBPStore(true);
                            getDataManager().setDataAreaId(response.body().getDataAreaID());
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            getDataManager().storeGlobalJson(json);
                            if (getDataManager().getGlobalJson().isISHBPStore())
                                getHBPConfigration();
                            else getTenderTypeApi();
                        } else {
                            getMvpView().hideLoading();
                            if (response.body() != null)
                                getMvpView().userLoginFailed(response.body().getReturnMessage());
                            else handleApiError(1);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetGlobalConfingRes> call, @NotNull Throwable t) {
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
    public void getHBPConfigration() {
        if (getMvpView().isNetworkConnected()) {
            //Creating an object of our api interface
//            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<HBPConfigResponse> call = api.GET_HBP_CONFING_RES_CALL(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<HBPConfigResponse>() {
                @Override
                public void onResponse(@NotNull Call<HBPConfigResponse> call, @NotNull Response<HBPConfigResponse> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        // getMvpView().hideLoading();
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            getDataManager().storeHBPConfiRes(json);
                            getTenderTypeApi();

                        } else {
                            getMvpView().hideLoading();
                            if (response.body() != null)
                                getMvpView().userLoginFailed(response.body().getReturnMessage());
                            else handleApiError(1);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HBPConfigResponse> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    public void getTenderTypeApi() {
        if (getMvpView().isNetworkConnected()) {
            //   getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<GetTenderTypeRes> call = api.GET_TENDER_TYPE_RES_CALL(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<GetTenderTypeRes>() {
                @Override
                public void onResponse(@NotNull Call<GetTenderTypeRes> call, @NotNull Response<GetTenderTypeRes> response) {
                    if (response.isSuccessful()) {
                        // getMvpView().hideLoading();
                        if (response.body() != null && response.body().getGetTenderTypeResult() != null && response.body().getGetTenderTypeResult().getRequestStatus() == 0) {
//                            Singletone.getInstance().tenderTypeResultEntity = response.body().getGetTenderTypeResult();

                            getDataManager().setTenderTypeResultEntity(response.body().getGetTenderTypeResult());

                            getAllowedPaymentMode();
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getGetTenderTypeResult().getReturnMessage());
                            } else {
                                handleApiError(1);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetTenderTypeRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    handleApiError(t);
                    //  getMvpView().showMessage(R.string.some_error);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    public void getAllowedPaymentMode() {
        if (getMvpView().isNetworkConnected()) {
            //   getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<AllowedPaymentModeRes> call = api.ALLOWED_PAYMENT_MODE_RES_CALL(getDataManager().getStoreId(), getDataManager().getTerminalId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<AllowedPaymentModeRes>() {
                @Override
                public void onResponse(@NotNull Call<AllowedPaymentModeRes> call, @NotNull Response<AllowedPaymentModeRes> response) {
                    if (response.isSuccessful()) {
                        // getMvpView().hideLoading();
                        if (response.body() != null && response.body().get_PaymentMethodList() != null && response.body().getRequestStatus() == 0) {
                            Gson gson = new Gson();

                            System.out.println("paymentallowed--->" + gson.toJson(response.body()));
                            getDataManager().storeAllowedPaymentMethod(response.body());
                            getGetTrackingWiseConfiguration();
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getReturnMessage());
                            } else {
                                handleApiError(1);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AllowedPaymentModeRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    handleApiError(t);
                    //  getMvpView().showMessage(R.string.some_error);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    public void getGetTrackingWiseConfiguration() {
        if (getMvpView().isNetworkConnected()) {
            //   getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<GetTrackingWiseConfing> call = api.GET_TRACKING_WISE_CONFING_CALL(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new Object());
            call.enqueue(new Callback<GetTrackingWiseConfing>() {
                @Override
                public void onResponse(@NotNull Call<GetTrackingWiseConfing> call, @NotNull Response<GetTrackingWiseConfing> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null && response.body().get_TrackingConfigration() != null && response.body().getRequestStatus() == 0) {
                            getDataManager().storeTrackingWiseConfiguration(response.body());
                            getMvpView().userLoginSuccess();
                        } else {
                            if (response.body() != null) {
                                getMvpView().showMessage(response.body().getReturnMessage());
                            } else {
                                handleApiError(1);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetTrackingWiseConfing> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    handleApiError(t);
                    //  getMvpView().showMessage(R.string.some_error);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onMposPosiflexApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            ADSPlayListRequest adsPlayListRequest = new ADSPlayListRequest();
            adsPlayListRequest.setScreen_id("MPOS_POSIFLEX");
            Call<ADSPlayListResponse> call = api.ADS_PLAY_LIST_RESPONSE_SINGLE(adsPlayListRequest);
            call.enqueue(new Callback<ADSPlayListResponse>() {
                @Override
                public void onResponse(@NotNull Call<ADSPlayListResponse> call, @NotNull Response<ADSPlayListResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getData().getListData().getRows().size() > 0) {
                            getDataManager().setPosiflexListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessMposPosiflex();
                            getDataManager().getPosiflexlistDataEntity();
                            getMvpView().hideLoading();

                        } else {

                            ListDataEntity listDataEntity = new ListDataEntity();
                            ArrayList<RowsEntity> rowsEntitiesList = new ArrayList<>();
                            RowsEntity rowsEntity = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList = new ArrayList<>();
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setName("posiflex.jpg");
                            fileEntity.setPath("3A145FD3EFB98955586B8206102DAA0F2815BCCEED0458D7E2D23677FA5FD2E14160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
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
                            fileEntity1.setName("posiflex2.jpg");
                            fileEntity1.setPath("C102577E35ED75475DDD1640251E18BF29FAB9510C639034CCA74AE1ADD2E20E4160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
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
                            fileEntity2.setName("posiflex3.jpg");
                            fileEntity2.setPath("848AA5FCA31FFAD8D3375ECA3E0827DC4228B4BF399A206BEAD7694236AAB0204160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList2.add(fileEntity2);
                            media_libraryEntity2.setFile(fileEntitiesList2);
                            playlist_mediaEntity2.setMedia_library(media_libraryEntity2);
                            rowsEntity2.setPlaylist_media(playlist_mediaEntity2);
                            rowsEntitiesList.add(rowsEntity2);

                            listDataEntity.setRows(rowsEntitiesList);
                            response.body().getData().setListData(listDataEntity);
                            getDataManager().setPosiflexListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessMposPosiflex();
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

    @Override
    public void onDownloadPosiflexCall(String filePath, String fileName, int pos) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            Call<ResponseBody> call = api.doDownloadFile("https://signage.apollopharmacy.app/zc-v3.1-fs-svc/2.0/ads/get/" + filePath);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        createPosiFlexFilePath(response.body(), fileName, true, pos);
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

    public List<RowsEntity> getPosiflextDataListEntity() {
        if (getDataManager().getPosiflexlistDataEntity().getRows().size() > 0) {
            return getDataManager().getPosiflexlistDataEntity().getRows();
        } else {
            return null;
        }
    }

    @Override
    public void createPosiFlexFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos) {
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
                // Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                }
                outputStream.flush();
                //  Log.d(TAG, destinationFile.getParent());
//                getMvpView().checkFileAvailability();
                getMvpView().onSucessMposPosiflex();
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

    @Override
    public boolean firstTimeFalse() {
        return getDataManager().isIntializePos();
    }

    @Override
    public void secondTimeTrue() {
        getDataManager().setIntializePos(true);
    }

    @Override
    public boolean enablescreens() {
        return getDataManager().isOpenScreens();
    }

    @Override
    public GetGlobalConfingRes getGlobalConfigurationObj() {
        return getDataManager().getGlobalJson();
    }

    @Override
    public void updatePatchApiCAll() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();

            UpdatePatchRequest updatePatchRequest = new UpdatePatchRequest();
            updatePatchRequest.setRequestStatus(0);
            updatePatchRequest.setReturnMessage("");
            updatePatchRequest.setPatchName("Mpos.apk v" + BuildConfig.VERSION_NAME);
            updatePatchRequest.setStatus(0);
            updatePatchRequest.setStoreID(getDataManager().getStoreId());
            updatePatchRequest.setTerminalID(getDataManager().getTerminalId());
            updatePatchRequest.setDataAreaID(getDataManager().getDataAreaId());
            updatePatchRequest.setChannel("5637145327");

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());

            Call<UpdatePatchResponse> call = api.UPDATE_PATCH_API_CALL(updatePatchRequest);
            call.enqueue(new Callback<UpdatePatchResponse>() {
                @Override
                public void onResponse(@NotNull Call<UpdatePatchResponse> call, @NotNull Response<UpdatePatchResponse> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            if (response.body().getRequestStatus() == 0) {
                                getMvpView().onSuccessUpdatePatchApiCAll(response.body());
                            } else {
                                Toast.makeText(getMvpView().getContext(), response.body().getReturnMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            getMvpView().onError("Something went wrong");
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UpdatePatchResponse> call, @NotNull Throwable t) {
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
    public void setIsV1Flow(boolean isV1Flow) {
        getDataManager().setV1Flow(isV1Flow);
    }


}
