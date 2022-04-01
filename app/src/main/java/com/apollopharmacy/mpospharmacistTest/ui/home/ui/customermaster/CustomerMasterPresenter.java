package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster;

import android.util.Log;
import android.util.Pair;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.FileEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Media_libraryEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Playlist_mediaEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMasterPresenter<V extends CustomerMasterMvpView> extends BasePresenter<V>
        implements CustomerMasterMvpPresenter<V> {

    @Inject
    public CustomerMasterPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDateClick() {
        getMvpView().onDateClick();
    }

    @Override
    public void onClickSubmit() {
        getMvpView().onSubmitClick();
    }

    /*@Override
    public void onClickAnniversary() {
        getMvpView().onAnniversaryClick();
    }*/

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public boolean enablescreens()
    {
        return getDataManager().isOpenScreens();
    }


    @Override
    public  void sendSmsservice(String mobilenumber)
    {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            String URl=getDataManager().getGlobalJson().getSMSAPI();

            //randaom Otp Number---->
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
           // System.out.println(formatted);
            String message="Your OTP is "+formatted+". To proceed with the transaction, please visit www.oneapollo.com and consent to our privacy policies and terms and conditions.";

            String[] separated = URl.split("SendSmsFortemplete?");
            String url=separated[0];
           // url=url+"SendSmsFortemplete?"+"to="+mobilenumber+"&message="+message;
            Log.d("Mobile number-->",mobilenumber);
            Log.d("Mobile number-->",url);

            ApiInterface api = ApiClient.getApiServiceOTp(url);
            Call<ModelMobileNumVerify> call = api.verifyMobileNumber(mobilenumber,message);
            call.enqueue(new Callback<ModelMobileNumVerify>() {
                @Override
                public void onResponse(@NotNull Call<ModelMobileNumVerify> call, @NotNull Response<ModelMobileNumVerify> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                      //  if (response.body().getStatus() == "OK") {
                            getMvpView().generateotpSuccess(response.body(), formatted);
                      //  }
                        /*else
                        {
                            getMvpView().generateotpFailed(response.body().getMessage());

                        }*/
                        // Log.d("mobile verication-->","sucess");

                    }
                }
                @Override
                public void onFailure(@NotNull Call<ModelMobileNumVerify> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }

    @Override
    public void handleCustomerAddService() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            AddCustomerReqModel addCustomerReqModel = new AddCustomerReqModel();
            addCustomerReqModel.setFirstName(getMvpView().getFirstName());
            addCustomerReqModel.setMiddleName("");
            addCustomerReqModel.setLastName("");
            addCustomerReqModel.setPostalAddress(getMvpView().getPostalAddress());
            addCustomerReqModel.setCity("");
            addCustomerReqModel.setState(getDataManager().getGlobalJson().getStateCode());
            addCustomerReqModel.setDistrict("");
            addCustomerReqModel.setZipCode(getMvpView().getZipCode());
            addCustomerReqModel.setEmail("");
            addCustomerReqModel.setTelephone("");
            addCustomerReqModel.setDataAreaId(getDataManager().getDataAreaId());
            addCustomerReqModel.setMobile(getMvpView().getMobile());
            addCustomerReqModel.setDOB(getMvpView().getDOB());
            addCustomerReqModel.setDOA("1900-01-01");
            addCustomerReqModel.setAge(0);
            addCustomerReqModel.setGender(getMvpView().getGenderOption());
            addCustomerReqModel.setMaritalStatus("");
            addCustomerReqModel.setDependentsNo(0);
            addCustomerReqModel.setCardNumber(getMvpView().getCardNumber());
            addCustomerReqModel.setRegistrationDate(getMvpView().getDateOfReg());
            addCustomerReqModel.setCorpId("");
            addCustomerReqModel.setCustId("");
            addCustomerReqModel.setStoreId(getDataManager().getStoreId());
            addCustomerReqModel.setAXDomain(getDataManager().getGlobalJson().getAXServiceDomain());
            addCustomerReqModel.setAXUserId(getDataManager().getGlobalJson().getAXServiceUsername());
            addCustomerReqModel.setAXPassword(getDataManager().getGlobalJson().getAXServicePassword());
            addCustomerReqModel.setCustomerCreationURL(getDataManager().getGlobalJson().getAXServiceURL());
            addCustomerReqModel.setRequestStatus(getDataManager().getGlobalJson().getRequestStatus());
            addCustomerReqModel.setReturnMessage(getDataManager().getGlobalJson().getReturnMessage());
            Call<AddCustomerResModel> call = api.ADD_CUSTOMER_SERVICE(addCustomerReqModel);
            call.enqueue(new Callback<AddCustomerResModel>() {
                @Override
                public void onResponse(@NotNull Call<AddCustomerResModel> call, @NotNull Response<AddCustomerResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        if (response.body().getRequestStatus() == 0) {
                            getMvpView().addCustomerSuccess(response.body());
                        } else {
                            getMvpView().addCustomerFailed(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddCustomerResModel> call, @NotNull Throwable t) {
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
        if (getDataManager().getlistDataEntity().getRows().size() > 0) {
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
               // Log.d(TAG, "File Size=" + fileSize);
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
          //  Log.d(TAG, "Failed to save the file!");
        }
    }

}