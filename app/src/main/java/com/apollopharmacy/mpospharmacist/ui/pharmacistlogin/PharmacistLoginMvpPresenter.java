package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;


import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface PharmacistLoginMvpPresenter<V extends PharmacistLoginMvpView> extends MvpPresenter<V> {

    void onClickLogin();

    void onClickCampaignClose();

    void onInstoreCLick();

    void onSelectCampaign();

    void getUserId();

    void getCampaign();

    void userLoginInStoreApi();

    void userLoginCampaignApi();

    void getGlobalConfigration();

    void onMposPosiflexApiCall();

    List<RowsEntity> getPosiflextDataListEntity();

    void onDownloadPosiflexCall(String filePath, String fileName, int pos);

    void createPosiFlexFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    boolean firstTimeFalse();

    void secondTimeTrue();

}
